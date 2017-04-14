# TigerIsland
CEN3031 Team Q's Project

## Features
There are no features missing as far as game functionality goes. We divided up a turn into a `TileAction` and a `BuildAction`. Our AI strategy prioritizes as follows:

###### TileAction
1. The AI tries to stack. It tries to find a valid combination of `(volcano hex, orientation)` that is a legal move and can nuke an opponent's settlement (that is of size 5+). If it succeeds in finding a valid combination, we choose this move.

2. If not, it places a tile (of a random orientation) at the edge of the currently played board. 

###### BuildAction
1. The AI tries to place a Totoro. If we have a Totoro in our inventory and the AI finds a settlement of size 5 or greater that does not already have a Totoro, we place it at a valid spot adjacent to that settlement and remove it from our inventory.

2. The AI tries to place a Tiger. We scan the board for a valid Tiger spot, and if we find one then we place a Tiger and remove it from our inventory.

3. The AI tries to expand. We try to find a settlement that we deemed are worthy of expanding (size < 5, we're adding at least 2 meeples, the total post-expansion size <= 6, and we have enough total meeples in our inventory). If we find an expansion that meets these requirements, we apply the move and remove the meeples from our inventory.

4. The AI founds a settlement. We BFS from the center of the board in an attempt to try and find a valid spot to found a settlement on. We pick the first valid one that it finds.

## Instructions
_Running from within IntelliJ and setting the Program Arguments is the preferred method._

1. If you're running from within IntelliJ:
  ```
  Run > Edit Configurations > Application > TigerIslandClient
  ```
  **Program Arguments:** `<host_name> <port> <username> <password> <tournament_password>`
  
2. If you've created a .jar file:
```java
java MyJarFile.jar <host_name> <port> <username> <password> <tournament_password>
```
## Common Pitfalls
1. Make sure that the server is up and running prior to running the client!
2. Make sure the Program Arguments (see Instructions step 1) are correct.

## General Architecture
1. The `main` method can be found in `TigerIslandClient.java`. This is where we set up a connection to the server by creating a `SocketReaderWriter`. We read messages from the server here, and dispatch the handling of each message to a new `Thread` via a `Runnable` that we created called `WritingRunnable`.

2. Each `WritingRunnable` creates a `MessageDispatcher` and provides it `ServerProtocol` objects. The dispatcher loops through each protocol and routes the message to it's corresponding protocol. The protocol provides a response via the `responseForMessage` method.

3. Each protocol relays the relevant information to its `ServerProtocolInfoCommunicator` object, which in this case is the `TournamentDriver` object that's created in `TigerIslandClient.java`. In some cases, the `TournamentDriver` provides the protocol a response and in other cases it does not.

4. The `TournamentDriver` object receives information via the `receiveInfo` method. It owns a `Tournament` object which is responsible for creating `Game` and `Player` objects. Each `Player` object has a `GameAI` object which is responsible for making move decisions, which happen in the `tileAction` and `buildAction` method, respectively.

5. Messages are routed back up and sent to the server via the same `SocketReaderWriter`. 

## Git Workflow
1. `git checkout master` // Get on your local `master` branch
2. `git fetch upstream` // Fetch the team's repo
3. `git rebase upstream/master` // Rebase the team's changes (you want to keep all the current history!)
4. `git branch -l "myBranchName"` // Create a new local branch for the changes you want to make
5. `git checkout myBranchName` // Get on your newly created branch
6. Make some changes...
7. `git add .` // Add the changes. The . is just a file path, this add's the current directory
8. `git commit -m "I made some changes"` // Commit the changes, leave a descriptive message
9. `git push origin myBranchName` // Push your changes to your **remote** fork

Now, go to the [team's repo](https://github.com/CEN3031Q/TigerIsland) and submit a Pull Request. It'll get merged when we see all is good!

## Notes
- Pat doesn't show up as a contributor for some reason. Probably because a lot of his commits were actually merged via other people's branches, etc. We're not entirely sure why he doesn't show up, but if you scroll through the commit history you'll see he has authored many commits.
