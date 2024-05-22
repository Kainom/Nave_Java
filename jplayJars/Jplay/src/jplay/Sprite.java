/*
 * The use this code commercially must only be done with permission of the author.
 * Any modification shall be advised and sent to the author.
 * The author is not responsible for any problem therefrom the use of this frameWork.
 *
 * @author Gefersom Cardoso Lima
 * Federal Fluminense University
 * Computer Science
 */

package jplay;

/**
 * Class responsible for controlling all actions and behaviors of sprite.
 */

public class Sprite extends Animation
{
    private double jumpVelocity = 5.3;//It's used for the jump,
    private double velocityY = 0;
    private double gravity = 0.098;
    private boolean onFloor = false;
    private int floor;

    /**
     * Constructor of the class. Creates a class with one frame.
     * @param fileName Path of the file.
     */
    public Sprite(String fileName)
    {
            this(fileName, 1);
    }

    /**
     * Constructor of the class.
     * @param fileName Path of the file.
     * @param numFrames number of frames.
     */
    public Sprite(String fileName, int numFrames)
    {
            super(fileName, numFrames);
            velocityY = 0;
    }

    /**
     * Method used to move the sprite by the x-axis. The keys used to move the
     * sprite are LEFT_KEY and RIGHT_KEY.
     * @param velocity speed of locomotion in pixels.
     */
    public void moveX(double velocity)
    {
            moveX(Keyboard.LEFT_KEY, Keyboard.RIGHT_KEY, velocity);
    }

    /**
     * Method used to move the sprite by the x-axis.
     * @param leftKey Key used to move the sprite for the left.
     * @param rightKey Key used to move the sprite for the right.
     * @param velocity speed of locomotion in pixels.
     */
    public void moveX(int leftKey, int rightKey, double velocity)
    {
            Keyboard keyboard = Window.getInstance().getKeyboard();
            if (keyboard.keyDown(leftKey)&& this.x > 1)
            {
                this.x -= velocity;
            }

            if (keyboard.keyDown(rightKey) && this.x + this.width < Window.getInstance().getWidth())
            {
                this.x += velocity;
            }
    }
    /**
     * Method used to move the sprite by y-axis. The keys used to move the
     * sprite are UP_KEY and DOWN_KEY.
     * @param velocity speed of locomotion in pixels.
     */
    public void moveY(double velocity)
    {
        this.moveY(Keyboard.UP_KEY, Keyboard.DOWN_KEY, velocity);
    }

    /**
     * Method used to move the sprite by y-axis.
     * @param upKey Key used to move the sprite for the left.
     * @param downKey Key used to move the sprite for the right.
     * @param velocity speed of locomotion in pixels.
     */
    public void moveY(int upKey, int downKey, double velocity)
    {
            Keyboard keyboard = Window.getInstance().getKeyboard();
            if (keyboard.keyDown(upKey) && this.y > 1)
            {
                this.y -= velocity;
            }

            if (keyboard.keyDown(downKey) && this.y + this.height < Window.getInstance().getHeight())
            {
               this.y += velocity;
            }
    }

    /**
     * Moves the sprite from a point to another.
     * @param x Target point on the x axis.
     * @param y Target point on the y axis.
     * @param velocity speed of locomotion in pixels.
    */
    public void moveTo(double x, double y, double velocity)
    {
            if (this.x < x && (this.x + this.width < Window.getInstance().getWidth()) )
            {
                this.x += velocity;
            }
            else
            {
                if (this.x > x)
                    this.x -= velocity;
            }

            if (this.y > y)
            {
                this.y -= velocity;
            }
            else
            {
                if (this.y < y)
                    this.y += velocity;
            }
    }

    /**
     * Sets the floor that will be used for the methods jump or fall.
     * @param floor Value of the coordinate x.
     */
    public void setFloor(int floor)
    {
            this.floor = floor;
    }

    /**
     * Return the value the current floor.
     * @return int
     */
    public int getFloor()
    {
            return floor;
    }

    /**
     * Makes the sprite jump.
     * @param jumpKey key used to start the jump.
     */
    public void jump(int jumpKey)
    {
            Keyboard keyboard = Window.getInstance().getKeyboard();
            if (keyboard.keyDown(jumpKey) && onFloor == true)
            {
                onFloor = false;
                velocityY = -jumpVelocity;
            }

            velocityY += gravity;
            this.y += velocityY;

            if ( this.y + this.height - floor > 0.0001 )
            {
                velocityY = 0;
                this.y = floor - this.height;
                onFloor = true;
            }
    }

    /**
     * Makes the sprite jump.
     * The key used is SPACE_KEY (SPACE_BAR).
     */
    public void jump()
    {
            jump(Keyboard.SPACE_KEY);
    }

    /**
     * Returns true if the sprite is executing the jump, false otherwise.
     * return boolean
     */
    public boolean isJumping()
    {
            return !onFloor;
    }

    /**
     * This method simulates the gravity.
     * It's necessary sets the floor before call this method.
     */
    public void fall()
    {
            if ( floor - this.y - this.height < 1 )
            {
                velocityY = 0;
                this.y = floor - this.height;
            }
            else
                velocityY += gravity;

            this.y += velocityY;
    }

    /**
     * Returns true if the sprite is on the floor, false otherwise.
     * @return boolean
     */
    public boolean isOnFloor()
    {
             return (this.y + this.height) >= floor;
    }

    /**
     * Sets the jump velocity for the sprite.
     * This velocity is used to control the height of jump.
     * @param velocity speed of locomotion in pixels.
     */
    public void setJumpVelocity(double velocity)
    {
            this.jumpVelocity = velocity;
    }

    /**
     * Returns the velocity of jump.
     * @return double
     */
    public double getJumpVelocity()
    {
            return this.jumpVelocity;
    }
    
    /**
     * Returns the moving speed in the Y axis.
     * @return double
     */
    public void setVelocityY(double velocityY)
    {
            this.velocityY = velocityY;
    }

    /**
     * Returns the moving speed in the Y axis.
     * @return double
     */
    public double getVelocityY()
    {
            return this.velocityY;
    }

    /**
     * Returns the value used for gravity.
     * @return double
     */
    public double getGravity()
    {
            return this.gravity;
    }

    /**
     * Sets the value for gravity.
     * @param gravity
     */
    public void setGravity(double gravity)
    {
            this.gravity = gravity;
    }

}
