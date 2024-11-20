import java.util.*;
import java.util.stream.*;

class Solution {

    public int solution(int[][] points, int[][] routes) {
      int answer = 0;
        
      List<Robot> robots = Arrays.stream(routes)
            .map(robotRoutes -> new Robot(points, robotRoutes))
            .collect(Collectors.toList());
        
      while (robots.size() != 0) {
        HashMap<Point, Integer> warningPointMap = new HashMap<Point, Integer>(); 

        robots.forEach(robot -> 
            warningPointMap.put(robot.getPoint(), (warningPointMap.getOrDefault(robot.getPoint(), 0) + 1))
        );

        answer += ((int) warningPointMap.values().stream().filter(samePosRobotCount -> samePosRobotCount > 1).count());
        robots = robots.stream()
            .peek(Robot::move)
            .filter(Robot::isNotFinished)
            .collect(Collectors.toList());
        }
      return answer;
    }

    class Robot {

        private Point now = new Point(0, 0);
        private int finishPointIdx = 1;
        private List<Point> points;
        private boolean isFinished = false;
        
        public Robot (int[][] points, int[] robotRoutes) {
            this.points = Arrays.stream(robotRoutes)
                .boxed()
                .map(robotRoute -> new Point(points[robotRoute - 1][0], points[robotRoute - 1][1]))
                .collect(Collectors.toList());
            this.now = this.points.get(0);
            //System.out.println(this.points);
        }
    
        public void move() {
            if (points.size() == finishPointIdx)  {
                isFinished = true;
                return;
            }

            Point target = points.get(finishPointIdx);

            if (target.y != now.y) {
                now.y = (target.y > now.y) ? now.y + 1 : now.y - 1;
            } else {
                now.x = (target.x > now.x) ? now.x + 1 : now.x - 1;
            }
            if (target.equals(now)) {
                finishPointIdx += 1;
            }

        }
        
        public Point getPoint() {
            return now;
        }
        
        
        public boolean isNotFinished() {
            return !isFinished;
        }

    }
}

class Point {
    public int x;
    public int y;
    
    public Point(int y, int x) {
        this.x = x;
        this.y = y;
    }
    
    public boolean equals(Object obj) {
        Point oth = (Point) obj;
        return this.x == oth.x && this.y == oth.y;
    }

    public int hashCode() {
        return Objects.hash(x, y);
    }
    
    public String toString() {
        return x + " : " + y;    
    }
}