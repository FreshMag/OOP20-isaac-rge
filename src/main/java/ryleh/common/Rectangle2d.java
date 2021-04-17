package ryleh.common;

public class Rectangle2d implements Shape2d {

    private final int height;
    private final int width;
    private Point2d upperLeft;
    private Point2d lowerLeft;
    private Point2d upperRight;
    private Point2d lowerRight;

    /**
     * This constructor is used to instantiate a 2D rectangle that is oriented horizontally.
     * @param width Width of the rectangle.
     * @param height Height of the rectangle.
     * @param upperLeftX X coordinate of the upper left vertex of the rectangle.
     * @param upperLeftY Y coordinate of the upper left vertex of the rectangle.
     */
    public Rectangle2d(final int width, final int height, final int upperLeftX, final int upperLeftY) {
        super();
        this.height = height;
        this.width = width;
        this.setUpperLeft(new Point2d(upperLeftX, upperLeftY));
        this.lowerLeft = new Point2d(upperLeftX, upperLeftY + height);
        this.upperRight = new Point2d(upperLeftX + width, upperLeftY);
        this.setLowerRight(getUpperLeft().sum(new Vector2d(this.getWidth(), this.getHeight())));

    }
    /**
     * This constructor is used to instantiate a 2D rectangle whose orientation is not "simply" horizontal.
     * @param upperLeft Upper left vertex of the rectangle.
     * @param lowerLeft Lower left vertex of the rectangle.
     * @param lowerRight Lower right vertex of the rectangle.
     * @param upperRight Upper right vertex of the rectangle.
     */
    public Rectangle2d(final Point2d upperLeft, final Point2d lowerLeft, final Point2d lowerRight, final Point2d upperRight) {
        super();
        this.setUpperLeft(upperLeft);
        this.lowerLeft = lowerLeft;
        this.setLowerRight(lowerRight);
        this.upperRight = upperRight;
        this.width = (int) new Vector2d(upperLeft, upperRight).module();
        this.height = (int) new Vector2d(upperLeft, lowerLeft).module();
    }
    /**
     * {@inheritDoc}
     */
    @Override
    public boolean contains(final Point2d position) {
        return position.getX() > this.getUpperLeft().getX()
               && position.getX() < this.upperRight.getX() 
               && position.getY() > this.getUpperLeft().getY() 
               && position.getY() < this.lowerLeft.getY();
    }
    /**
     * Used to determine if a rectangle is contained completely in this rectangle.
     * @param rectangle Rectangle inside this rectangle.
     * @return True if all vertices of the inner rectangle are contained into the outer rectangle.
     */
    public boolean contains(final Rectangle2d rectangle) {
        return this.contains(rectangle.getUpperLeft()) 
                && this.contains(rectangle.getLowerRight())
                && this.contains(rectangle.upperRight)
                && this.contains(rectangle.lowerLeft);
    }
    /**
     * {@inheritDoc}
     */
    @Override
    public void setPosition(final Point2d position) {
        final Vector2d transform = new Vector2d(this.getUpperLeft(), position);
        this.setUpperLeft(this.getUpperLeft().sum(transform));
        this.upperRight = this.upperRight.sum(transform);
        this.lowerLeft = this.lowerLeft.sum(transform);
        this.setLowerRight(this.getLowerRight().sum(transform));
    }
    /**
     * {@inheritDoc}
     */
    @Override
    public Point2d getPosition() {
        return this.getUpperLeft();
    }
    /**
     * {@inheritDoc}
     */
    @Override
    public boolean intersect(final Shape2d shape) {
        return false;
    }
    /**
     * {@inheritDoc}
     */
    @Override
    public Point2d getCenter() {
        return new Point2d((this.getUpperLeft().getX() + this.getLowerRight().getX()) / 2, 
                this.getUpperLeft().getY() + this.getLowerRight().getY() / 2);
    }
    public Point2d getUpperLeft() {
        return upperLeft;
    }
    public void setUpperLeft(final Point2d upperLeft) {
        this.upperLeft = upperLeft;
    }
    public Point2d getLowerRight() {
        return lowerRight;
    }
    public void setLowerRight(Point2d lowerRight) {
        this.lowerRight = lowerRight;
    }
    public int getHeight() {
        return height;
    }
    public int getWidth() {
        return width;
    }

}
