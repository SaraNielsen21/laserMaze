import java.util.*;
import java.util.Scanner;
import java.io.*;

public class LaserMaze {
    static int mazeHeight;
    static int mazeWidth;
    static int numOfMirrors;
    static String mazeDimensions;
    static String entry;
    static String exit;

    static String[][] maze;
    static List<String> mirrors = new ArrayList<String>();
    static String[] mazeDef;
    //static String[] mirrors = new String[numOfMirrors];

    //read the file entered by the user and assign variables
    public static void readFile(String fileName)
    {
        try{
            FileReader fileReader = new FileReader(fileName);
            BufferedReader br = new BufferedReader(fileReader);
            List<String> mazeList = new ArrayList<String>();
            String line;

            while((line = br.readLine()) != null) {
                mazeList.add(line);
            }
            fileReader.close();
            br.close();

            mazeDef = new String[mazeList.size()];
            mazeDef = mazeList.toArray(mazeDef);

        }
        catch(FileNotFoundException e){
            System.out.println("File not found");
        }
        catch(IOException e){
            e.printStackTrace();
        }
    }

    public static void constructMaze(){
        int count = 0;
        for(int i = 0; i < mazeDef.length; i++) 
        {
            if(i == 0) mazeDimensions = mazeDef[i];
            if(mazeDef[i].equals("-1")) 
            {
                count++;
            }
            else if(count == 1)
            {
                numOfMirrors++;
                mirrors.add(mazeDef[i]);
            }
            else if (count == 2) entry = mazeDef[i];
        }

        mazeWidth = Integer.parseInt(mazeDimensions.substring(0,1));
        mazeHeight = Integer.parseInt(mazeDimensions.substring(2));

        maze = new String[mazeHeight][mazeWidth];
        for(int i = 0; i < maze.length; i++) {
            for(int j = 0; j < maze[0].length; j++)
            {
                maze[i][j] = "0";
            }
        }

        for(String s : mirrors)
        {
            int width = Integer.parseInt(s.substring(0,1));
            int height = Integer.parseInt(s.substring(2,3));
            String type = s.substring(3);

            maze[height][width] = type;
        }

    }

    public static String findPath()
    {
        exit = "";
        boolean done = false;
        int height = Integer.parseInt(entry.substring(2,3));
        int width = Integer.parseInt(entry.substring(0,1));
        String initialDirection = entry.substring(3); //Vertical or Horizontal
        String finalDirection = ""; //Vertical or Horizontal
        String direction = ""; //North, South, East, or West

        if(initialDirection.equals("V"))
        {
            if(height == 0) direction = "N";
            else direction = "S";
        }
        if(initialDirection.equals("H"))
        {
            if(width == 0) direction = "E";
            else direction = "W";
        }

        while(!done)
        {
            System.out.println("Path: " + width + "," + height + " " + direction);
            if(maze[height][width].equals("0"))
            { 
                if(direction.equals("N")) //laser is vertical
                {
                    height++;
                }
                else if(direction.equals("S"))
                {
                    height--;
                }
                else if(direction.equals("E"))
                {
                    width++;
                }
                else if(direction.equals("W"))
                {
                    width--;
                }
            }
            else if(maze[height][width].equals("R"))
            {
                if(direction.equals("N")) 
                {
                    direction = "E";
                    width++;
                }
                else if(direction.equals("S"))
                {
                    direction = "W";
                    width--;
                }
                else if(direction.equals("E"))
                {
                    direction = "N";
                    height++;
                }
                else if(direction.equals("W"))
                {
                    direction = "S";
                    height--;
                }
            }
            else if(maze[height][width].equals("RR"))
            {
                if(direction.equals("N")) 
                {
                    direction = "E";
                    width++;
                }
                else if(direction.equals("S"))
                {
                    height--;
                }
                else if(direction.equals("E"))
                {
                    width++;
                }
                else if(direction.equals("W"))
                {
                    direction = "S";
                    height--;
                }
            }
            else if(maze[height][width].equals("RL"))
            {
                if(direction.equals("N")) 
                {
                    height++;
                }
                else if(direction.equals("S"))
                {
                    direction = "W";
                    width--;
                }
                else if(direction.equals("E"))
                {
                    direction = "N";
                    height++;
                }
                else if(direction.equals("W"))
                {
                    width--;
                }
            }
            else if(maze[height][width].equals("L"))
            {
                if(direction.equals("N")) 
                {
                    direction = "W";
                    width--;
                }
                else if(direction.equals("S"))
                {
                    direction = "E";
                    width++;
                }
                else if(direction.equals("E"))
                {
                    direction = "S";
                    height--;
                }
                else if(direction.equals("W"))
                {
                    direction = "N";
                    height++;
                } 
            }
            else if(maze[height][width].equals("LL"))
            {
                if(direction.equals("N")) 
                {
                    direction = "W";
                    width--;
                }
                else if(direction.equals("S"))
                {
                    height--;
                }
                else if(direction.equals("E"))
                {
                    direction = "S";
                    height--;
                }
                else if(direction.equals("W"))
                {
                    width--;
                } 
            }
            else if(maze[height][width].equals("LR"))
            {
                if(direction.equals("N")) 
                {
                    height++;
                }
                else if(direction.equals("S"))
                {
                    direction = "E";
                    width++;
                }
                else if(direction.equals("E"))
                {
                    width++;
                }
                else if(direction.equals("W"))
                {
                    direction = "N";
                    height++;
                } 
            }
            done = exit(height, width, direction);
        }
        System.out.println("Path: " + width + "," + height + " " + direction);
        if(direction.equals("N") || direction.equals("S")) finalDirection = "V";
        else finalDirection = "H";
        exit = width + "," + height + finalDirection;
        return exit;
    }

    public static boolean exit(int height, int width, String direction)
    {
        boolean exit = false;
        if(direction.equals("N") && height == (maze[0].length - 1)) exit = true;
        else if(direction.equals("S") && height == 0) exit = true;
        else if(direction.equals("E") && width == (maze[1].length - 1)) exit = true;
        else if(direction.equals("W") && width == 0) exit = true;
        else exit = false;
        return exit;
    }

    public static void main(String []args) {

        //get filename as input from user
        Scanner input = new Scanner(System.in);
        System.out.println("Enter filename: ");
        String filename = input.nextLine();

        //call readFile method to set variables with appropriate values
        readFile(filename);
        //construct the maze
        constructMaze();
        //call method to find exit room
        findPath();

        //print results
        System.out.println("Board Dimensions = " + mazeDimensions);
        System.out.println("Entry room = " + entry);
        System.out.println("Exit room = " + exit);
    }
}