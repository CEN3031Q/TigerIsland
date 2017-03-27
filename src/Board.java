import java.util.ArrayList;

public class Board {
    Hexagon[][] gameBoard = new Hexagon[400][400];
    private int nextTileID = 1;
    private int minBoardX = 400;
    private int maxBoardX = 0;
    private int minBoardY = 400;
    private int maxBoardY = 0;

    public Board() {
        for (int ii = 0; ii < 400; ii++) {
            for (int jj = 0; jj < 400; jj++) {
                gameBoard[ii][jj] = new Hexagon();
                if (ii % 2 == 0 && jj % 2 == 0) {
                    gameBoard[ii][jj].setSpaceAsValid(true);
                } else if (ii % 2 == 1 && jj % 2 == 1) {
                    gameBoard[ii][jj].setSpaceAsValid(true);
                } else {
                    gameBoard[ii][jj].setSpaceAsValid(false);
                }
            }
        }
        //trying to fin the min and max to print from
        for (int ii = 0; ii < 400; ii++) {
            for (int jj = 0; jj < 400; jj++) {
                if(gameBoard[ii][jj].getTileID() != 0){
                    if(ii > maxBoardX){
                        maxBoardX = ii;
                    }
                    if(jj > maxBoardY){
                        maxBoardY = jj;
                    }
                    if(ii < minBoardX){
                        minBoardX = ii;
                    }
                    if(jj < minBoardY){
                        minBoardY = jj;
                    }
                }
            }
        }
        //making sure we don't go less than 0 or greater than 400
        if(minBoardX >= 2){
            minBoardX = minBoardX - 2;
        }
        if(minBoardY >= 2){
            minBoardY = minBoardX - 2;
        }
        if(maxBoardX <= 398){
            maxBoardX = maxBoardX + 2;
        }
        if(maxBoardY <= 398){
            maxBoardY = maxBoardX + 2;
        }

    }

    //take in a coordinate instead of the x y. x and y is just the anchor's position
    //make it void
    //don't need rule enforcement
    //change the name of setLevel needs to become incrementLevel
    //make it not take inputs. just increment
    //
    public void placeTile(Tile tileToPlace, Coordinate tileCoordinate) {
        int xCoordinate = tileCoordinate.getX();
        int yCoordinate = tileCoordinate.getY();


        //this is where it changes all of the values
        //change the bounds if i loop based off of the anchor
        //or don't even loop, just get the left and right locations and change the values
        for (int ii = yCoordinate; ii < yCoordinate+2; ii++) {
            for (int jj = xCoordinate; jj < xCoordinate+3; jj++) {
                gameBoard[ii][jj].incrementLevel(1);
                gameBoard[ii][jj].setTileID(nextTileID);
                gameBoard[ii][jj].setOccupied(false);
            }
        }
        this.nextTileID++;

        TerrainType left = tileToPlace.getTerrainTypeForPosition(HexagonPosition.LEFT);
        TerrainType right = tileToPlace.getTerrainTypeForPosition(HexagonPosition.RIGHT);
        TerrainType middle = tileToPlace.getTerrainTypeForPosition(HexagonPosition.MIDDLE);


        //Here I actually go to each of the coordinates and set the hexes' terrain types based on the Orientation and anchor
        if(tileToPlace.getOrientation() == TileOrientation.TOPHEAVY && tileToPlace.getAnchorPosition() == HexagonPosition.MIDDLE) {
            gameBoard[yCoordinate][xCoordinate].setTerrainType(middle);
            gameBoard[yCoordinate - 1][xCoordinate - 1].setTerrainType(left);
            gameBoard[yCoordinate - 1][xCoordinate + 1].setTerrainType(right);
        }
        else if(tileToPlace.getOrientation() == TileOrientation.TOPHEAVY && tileToPlace.getAnchorPosition() == HexagonPosition.LEFT) {
            gameBoard[yCoordinate][xCoordinate].setTerrainType(left);
            gameBoard[yCoordinate + 1][xCoordinate + 1].setTerrainType(middle);
            gameBoard[yCoordinate][xCoordinate + 2].setTerrainType(right);
        }
        else if(tileToPlace.getOrientation() == TileOrientation.TOPHEAVY && tileToPlace.getAnchorPosition() == HexagonPosition.RIGHT) {
            gameBoard[yCoordinate][xCoordinate].setTerrainType(right);
            gameBoard[yCoordinate][xCoordinate - 2].setTerrainType(left);
            gameBoard[yCoordinate + 1][xCoordinate - 1].setTerrainType(middle);
        }
        else if (tileToPlace.getOrientation() == TileOrientation.BOTTOMHEAVY && tileToPlace.getAnchorPosition() == HexagonPosition.MIDDLE){
            gameBoard[yCoordinate][xCoordinate].setTerrainType(middle);
            gameBoard[yCoordinate - 1][xCoordinate - 1].setTerrainType(left);
            gameBoard[yCoordinate - 1][xCoordinate + 1].setTerrainType(right);
        }
        else if (tileToPlace.getOrientation() == TileOrientation.BOTTOMHEAVY && tileToPlace.getAnchorPosition() == HexagonPosition.LEFT){
            gameBoard[yCoordinate][xCoordinate].setTerrainType(left);
            gameBoard[yCoordinate + 1][xCoordinate + 1].setTerrainType(middle);
            gameBoard[yCoordinate][xCoordinate + 2].setTerrainType(right);
        }
        else if (tileToPlace.getOrientation() == TileOrientation.BOTTOMHEAVY && tileToPlace.getAnchorPosition() == HexagonPosition.RIGHT){
            gameBoard[yCoordinate][xCoordinate].setTerrainType(right);
            gameBoard[yCoordinate - 1][xCoordinate - 1].setTerrainType(middle);
            gameBoard[yCoordinate][xCoordinate - 2].setTerrainType(left);
        }
    }
    public ArrayList<Coordinate> determineValidPositionsForNewTile(Tile tileToBePlaced) {
        ArrayList<Coordinate> validCoordinateList = new ArrayList<>();

        //If checking the first tile to be placed
        if (this.nextTileID == 1) {
            Coordinate validCoordinate = new Coordinate(200, 200);
            validCoordinateList.add(validCoordinate);
            return validCoordinateList;
        }

        //TODO: Replace bounds of xCoordinate and yCoordinate in the for loop with min and max bounds of board in play
        for (int yCoordinate = 3; yCoordinate <= 396; yCoordinate++) {
            for (int xCoordinate = 3; xCoordinate <= 396; xCoordinate++) {

                //If the current x,y position is not a valid place to put the anchor continue
                if (!gameBoard[yCoordinate][xCoordinate].getSpaceIsValid())
                    continue;

                if (tileToBePlaced.getOrientation() == TileOrientation.TOPHEAVY) {

                    if (tileToBePlaced.getAnchorPosition() == HexagonPosition.MIDDLE) {
                        //Checks if positions within the new tile are going to overlap existing tiles
                        if (gameBoard[yCoordinate-1][xCoordinate-1].getTileID() != 0)
                            continue;
                        if (gameBoard[yCoordinate-1][xCoordinate].getTileID() != 0)
                            continue;
                        if (gameBoard[yCoordinate-1][xCoordinate+1].getTileID() != 0)
                            continue;
                        if (gameBoard[yCoordinate][xCoordinate-1].getTileID() != 0)
                            continue;
                        if (gameBoard[yCoordinate][xCoordinate].getTileID() != 0)
                            continue;
                        if (gameBoard[yCoordinate][xCoordinate+1].getTileID() != 0)
                            continue;

                        //Check if new tile is adjacent to existing tiles
                        //If we found one no need to check the rest
                        if (gameBoard[yCoordinate-2][xCoordinate-2].getTileID() != 0) {
                            Coordinate validCoordinate = new Coordinate(xCoordinate, yCoordinate);
                            validCoordinateList.add(validCoordinate);
                        } else if (gameBoard[yCoordinate-2][xCoordinate].getTileID() != 0) {
                            Coordinate validCoordinate = new Coordinate(xCoordinate, yCoordinate);
                            validCoordinateList.add(validCoordinate);
                        } else if (gameBoard[yCoordinate-2][xCoordinate+2].getTileID() != 0) {
                            Coordinate validCoordinate = new Coordinate(xCoordinate, yCoordinate);
                            validCoordinateList.add(validCoordinate);
                        } else if (gameBoard[yCoordinate][xCoordinate+2].getTileID() != 0) {
                            Coordinate validCoordinate = new Coordinate(xCoordinate, yCoordinate);
                            validCoordinateList.add(validCoordinate);
                        } else if (gameBoard[yCoordinate+1][xCoordinate+1].getTileID() != 0) {
                            Coordinate validCoordinate = new Coordinate(xCoordinate, yCoordinate);
                            validCoordinateList.add(validCoordinate);
                        } else if (gameBoard[yCoordinate+1][xCoordinate-1].getTileID() != 0) {
                            Coordinate validCoordinate = new Coordinate(xCoordinate, yCoordinate);
                            validCoordinateList.add(validCoordinate);
                        } else if (gameBoard[yCoordinate][xCoordinate-2].getTileID() != 0) {
                            Coordinate validCoordinate = new Coordinate(xCoordinate, yCoordinate);
                            validCoordinateList.add(validCoordinate);
                        }

                    } else if (tileToBePlaced.getAnchorPosition() == HexagonPosition.LEFT) {
                        //Checks if positions within the new tile are going to overlap existing tiles
                        if (gameBoard[yCoordinate+1][xCoordinate].getTileID() != 0)
                            continue;
                        if (gameBoard[yCoordinate+1][xCoordinate+1].getTileID() != 0)
                            continue;
                        if (gameBoard[yCoordinate+1][xCoordinate+2].getTileID() != 0)
                            continue;
                        if (gameBoard[yCoordinate][xCoordinate].getTileID() != 0)
                            continue;
                        if (gameBoard[yCoordinate][xCoordinate+1].getTileID() != 0)
                            continue;
                        if (gameBoard[yCoordinate][xCoordinate+2].getTileID() != 0)
                            continue;

                        //Check if new tile is adjacent to existing tiles
                        //If we found one no need to check the rest
                        if (gameBoard[yCoordinate-1][xCoordinate-1].getTileID() != 0) {
                            Coordinate validCoordinate = new Coordinate(xCoordinate, yCoordinate);
                            validCoordinateList.add(validCoordinate);
                        } else if (gameBoard[yCoordinate-1][xCoordinate+1].getTileID() != 0) {
                            Coordinate validCoordinate = new Coordinate(xCoordinate, yCoordinate);
                            validCoordinateList.add(validCoordinate);
                        } else if (gameBoard[yCoordinate-1][xCoordinate+3].getTileID() != 0) {
                            Coordinate validCoordinate = new Coordinate(xCoordinate, yCoordinate);
                            validCoordinateList.add(validCoordinate);
                        } else if (gameBoard[yCoordinate+1][xCoordinate-1].getTileID() != 0) {
                            Coordinate validCoordinate = new Coordinate(xCoordinate, yCoordinate);
                            validCoordinateList.add(validCoordinate);
                        } else if (gameBoard[yCoordinate+1][xCoordinate+3].getTileID() != 0) {
                            Coordinate validCoordinate = new Coordinate(xCoordinate, yCoordinate);
                            validCoordinateList.add(validCoordinate);
                        } else if (gameBoard[yCoordinate+2][xCoordinate].getTileID() != 0) {
                            Coordinate validCoordinate = new Coordinate(xCoordinate, yCoordinate);
                            validCoordinateList.add(validCoordinate);
                        } else if (gameBoard[yCoordinate+2][xCoordinate+2].getTileID() != 0) {
                            Coordinate validCoordinate = new Coordinate(xCoordinate, yCoordinate);
                            validCoordinateList.add(validCoordinate);
                        }

                    } else if (tileToBePlaced.getAnchorPosition() == HexagonPosition.RIGHT) {
                        //Checks if positions within the new tile are going to overlap existing tiles
                        if (gameBoard[yCoordinate+1][xCoordinate-2].getTileID() != 0)
                            continue;
                        if (gameBoard[yCoordinate+1][xCoordinate-1].getTileID() != 0)
                            continue;
                        if (gameBoard[yCoordinate+1][xCoordinate].getTileID() != 0)
                            continue;
                        if (gameBoard[yCoordinate][xCoordinate-2].getTileID() != 0)
                            continue;
                        if (gameBoard[yCoordinate][xCoordinate-1].getTileID() != 0)
                            continue;
                        if (gameBoard[yCoordinate][xCoordinate].getTileID() != 0)
                            continue;

                        //Check if new tile is adjacent to existing tiles
                        //If we found one no need to check the rest
                        if (gameBoard[yCoordinate-1][xCoordinate-3].getTileID() != 0) {
                            Coordinate validCoordinate = new Coordinate(xCoordinate, yCoordinate);
                            validCoordinateList.add(validCoordinate);
                        } else if (gameBoard[yCoordinate-1][xCoordinate-1].getTileID() != 0) {
                            Coordinate validCoordinate = new Coordinate(xCoordinate, yCoordinate);
                            validCoordinateList.add(validCoordinate);
                        } else if (gameBoard[yCoordinate-1][xCoordinate+1].getTileID() != 0) {
                            Coordinate validCoordinate = new Coordinate(xCoordinate, yCoordinate);
                            validCoordinateList.add(validCoordinate);
                        } else if (gameBoard[yCoordinate+1][xCoordinate+1].getTileID() != 0) {
                            Coordinate validCoordinate = new Coordinate(xCoordinate, yCoordinate);
                            validCoordinateList.add(validCoordinate);
                        } else if (gameBoard[yCoordinate+1][xCoordinate-3].getTileID() != 0) {
                            Coordinate validCoordinate = new Coordinate(xCoordinate, yCoordinate);
                            validCoordinateList.add(validCoordinate);
                        } else if (gameBoard[yCoordinate+2][xCoordinate].getTileID() != 0) {
                            Coordinate validCoordinate = new Coordinate(xCoordinate, yCoordinate);
                            validCoordinateList.add(validCoordinate);
                        } else if (gameBoard[yCoordinate+2][xCoordinate-2].getTileID() != 0) {
                            Coordinate validCoordinate = new Coordinate(xCoordinate, yCoordinate);
                            validCoordinateList.add(validCoordinate);
                        }
                    }

                } else if (tileToBePlaced.getOrientation() == TileOrientation.BOTTOMHEAVY) {

                    if (tileToBePlaced.getAnchorPosition() == HexagonPosition.MIDDLE) {
                        //Checks if positions within the new tile are going to overlap existing tiles
                        if (gameBoard[yCoordinate+1][xCoordinate-1].getTileID() != 0)
                            continue;
                        if (gameBoard[yCoordinate+1][xCoordinate].getTileID() != 0)
                            continue;
                        if (gameBoard[yCoordinate+1][xCoordinate+1].getTileID() != 0)
                            continue;
                        if (gameBoard[yCoordinate][xCoordinate-1].getTileID() != 0)
                            continue;
                        if (gameBoard[yCoordinate][xCoordinate].getTileID() != 0)
                            continue;
                        if (gameBoard[yCoordinate][xCoordinate+1].getTileID() != 0)
                            continue;

                        //Check if new tile is adjacent to existing tiles
                        //If we found one no need to check the rest
                        if (gameBoard[yCoordinate+2][xCoordinate-2].getTileID() != 0) {
                            Coordinate validCoordinate = new Coordinate(xCoordinate, yCoordinate);
                            validCoordinateList.add(validCoordinate);
                        } else if (gameBoard[yCoordinate+2][xCoordinate].getTileID() != 0) {
                            Coordinate validCoordinate = new Coordinate(xCoordinate, yCoordinate);
                            validCoordinateList.add(validCoordinate);
                        } else if (gameBoard[yCoordinate+2][xCoordinate+2].getTileID() != 0) {
                            Coordinate validCoordinate = new Coordinate(xCoordinate, yCoordinate);
                            validCoordinateList.add(validCoordinate);
                        } else if (gameBoard[yCoordinate][xCoordinate+2].getTileID() != 0) {
                            Coordinate validCoordinate = new Coordinate(xCoordinate, yCoordinate);
                            validCoordinateList.add(validCoordinate);
                        } else if (gameBoard[yCoordinate][xCoordinate-2].getTileID() != 0) {
                            Coordinate validCoordinate = new Coordinate(xCoordinate, yCoordinate);
                            validCoordinateList.add(validCoordinate);
                        } else if (gameBoard[yCoordinate-1][xCoordinate-1].getTileID() != 0) {
                            Coordinate validCoordinate = new Coordinate(xCoordinate, yCoordinate);
                            validCoordinateList.add(validCoordinate);
                        } else if (gameBoard[yCoordinate-1][xCoordinate+1].getTileID() != 0) {
                            Coordinate validCoordinate = new Coordinate(xCoordinate, yCoordinate);
                            validCoordinateList.add(validCoordinate);
                        }

                    } else if (tileToBePlaced.getAnchorPosition() == HexagonPosition.LEFT) {
                        //Checks if positions within the new tile are going to overlap existing tiles
                        if (gameBoard[yCoordinate-1][xCoordinate].getTileID() != 0)
                            continue;
                        if (gameBoard[yCoordinate-1][xCoordinate+1].getTileID() != 0)
                            continue;
                        if (gameBoard[yCoordinate-1][xCoordinate+2].getTileID() != 0)
                            continue;
                        if (gameBoard[yCoordinate][xCoordinate].getTileID() != 0)
                            continue;
                        if (gameBoard[yCoordinate][xCoordinate+1].getTileID() != 0)
                            continue;
                        if (gameBoard[yCoordinate][xCoordinate+2].getTileID() != 0)
                            continue;

                        //Check if new tile is adjacent to existing tiles
                        //If we found one no need to check the rest
                        if (gameBoard[yCoordinate-1][xCoordinate-1].getTileID() != 0) {
                            Coordinate validCoordinate = new Coordinate(xCoordinate, yCoordinate);
                            validCoordinateList.add(validCoordinate);
                        } else if (gameBoard[yCoordinate-1][xCoordinate+3].getTileID() != 0) {
                            Coordinate validCoordinate = new Coordinate(xCoordinate, yCoordinate);
                            validCoordinateList.add(validCoordinate);
                        } else if (gameBoard[yCoordinate-2][xCoordinate].getTileID() != 0) {
                            Coordinate validCoordinate = new Coordinate(xCoordinate, yCoordinate);
                            validCoordinateList.add(validCoordinate);
                        } else if (gameBoard[yCoordinate-2][xCoordinate+2].getTileID() != 0) {
                            Coordinate validCoordinate = new Coordinate(xCoordinate, yCoordinate);
                            validCoordinateList.add(validCoordinate);
                        } else if (gameBoard[yCoordinate+1][xCoordinate-1].getTileID() != 0) {
                            Coordinate validCoordinate = new Coordinate(xCoordinate, yCoordinate);
                            validCoordinateList.add(validCoordinate);
                        } else if (gameBoard[yCoordinate+1][xCoordinate+1].getTileID() != 0) {
                            Coordinate validCoordinate = new Coordinate(xCoordinate, yCoordinate);
                            validCoordinateList.add(validCoordinate);
                        } else if (gameBoard[yCoordinate+1][xCoordinate+3].getTileID() != 0) {
                            Coordinate validCoordinate = new Coordinate(xCoordinate, yCoordinate);
                            validCoordinateList.add(validCoordinate);
                        }

                    } else if (tileToBePlaced.getAnchorPosition() == HexagonPosition.RIGHT) {
                        //Checks if positions within the new tile are going to overlap existing tiles
                        if (gameBoard[yCoordinate-1][xCoordinate].getTileID() != 0)
                            continue;
                        if (gameBoard[yCoordinate-1][xCoordinate-1].getTileID() != 0)
                            continue;
                        if (gameBoard[yCoordinate-1][xCoordinate-2].getTileID() != 0)
                            continue;
                        if (gameBoard[yCoordinate][xCoordinate].getTileID() != 0)
                            continue;
                        if (gameBoard[yCoordinate][xCoordinate-1].getTileID() != 0)
                            continue;
                        if (gameBoard[yCoordinate][xCoordinate-2].getTileID() != 0)
                            continue;

                        //Check if new tile is adjacent to existing tiles
                        //If we found one no need to check the rest
                        if (gameBoard[yCoordinate-1][xCoordinate-3].getTileID() != 0) {
                            Coordinate validCoordinate = new Coordinate(xCoordinate, yCoordinate);
                            validCoordinateList.add(validCoordinate);
                        } else if (gameBoard[yCoordinate-1][xCoordinate+1].getTileID() != 0) {
                            Coordinate validCoordinate = new Coordinate(xCoordinate, yCoordinate);
                            validCoordinateList.add(validCoordinate);
                        } else if (gameBoard[yCoordinate-2][xCoordinate].getTileID() != 0) {
                            Coordinate validCoordinate = new Coordinate(xCoordinate, yCoordinate);
                            validCoordinateList.add(validCoordinate);
                        } else if (gameBoard[yCoordinate-2][xCoordinate-2].getTileID() != 0) {
                            Coordinate validCoordinate = new Coordinate(xCoordinate, yCoordinate);
                            validCoordinateList.add(validCoordinate);
                        } else if (gameBoard[yCoordinate+1][xCoordinate+1].getTileID() != 0) {
                            Coordinate validCoordinate = new Coordinate(xCoordinate, yCoordinate);
                            validCoordinateList.add(validCoordinate);
                        } else if (gameBoard[yCoordinate+1][xCoordinate-1].getTileID() != 0) {
                            Coordinate validCoordinate = new Coordinate(xCoordinate, yCoordinate);
                            validCoordinateList.add(validCoordinate);
                        } else if (gameBoard[yCoordinate+1][xCoordinate-3].getTileID() != 0) {
                            Coordinate validCoordinate = new Coordinate(xCoordinate, yCoordinate);
                            validCoordinateList.add(validCoordinate);
                        }
                    }
                }
            }
        }

        return validCoordinateList;
    }



    public void printBoard() {
        System.out.println("Terrain, TileID, Level");
        for (int ii = minBoardX; ii < maxBoardX; ii++) {
            for (int jj = minBoardY; jj < maxBoardY; jj++) {
                System.out.print("(" + gameBoard[ii][jj].getTerrainType() + ", " + gameBoard[ii][jj].getTileID() + ", " + gameBoard[ii][jj].getLevel() + ")");
            }
            System.out.println();
        }
    }

    public TerrainType getTerrainTypeAtPosition(int xPosition, int yPosition) {
        return gameBoard[yPosition][xPosition].getTerrainType();
    }

    public int getLevelAtPosition(int xPosition, int yPosition) {
        return gameBoard[yPosition][xPosition].getLevel();
    }

    public int getTileIDAtPosition(int xPosition, int yPosition) {
        return gameBoard[yPosition][xPosition].getTileID();
    }

    public void setVillagersAtPosition(int numOfVillagers, int xPosition, int yPosition){
        gameBoard[yPosition][xPosition].setVillagersOnTop(numOfVillagers);
    }

    public int getVillagerNumberAtPosition(int xPosition, int yPosition) { return gameBoard[yPosition][xPosition].getNumVillagersOnTop(); }
}