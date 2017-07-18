package sophia.com.ecommerce2.data;

/**
 * Created by archimede on 06/06/17.
 */

public class Item {
    private int mId;
    private int category;
    private String name;
    private String description;
    private double price;
    private String photoItem;

    @Override
    public String toString() {
        return "Item{" +
                "mId=" + mId +
                ", category=" + category +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", photoItem='" + photoItem + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Item item = (Item) o;

        if (Double.compare(item.price, price) != 0) return false;
        if (name != null ? !name.equals(item.name) : item.name != null) return false;
        if (description != null ? !description.equals(item.description) : item.description != null)
            return false;
        return photoItem != null ? photoItem.equals(item.photoItem) : item.photoItem == null;

    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = name != null ? name.hashCode() : 0;
        result = 31 * result + (description != null ? description.hashCode() : 0);
        temp = Double.doubleToLongBits(price);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + (photoItem != null ? photoItem.hashCode() : 0);
        return result;
    }

    public int getmId() {
        return mId;
    }

    public void setmId(int mId) {
        this.mId = mId;
    }

    public Item(){

    }
    public Item(String name, String description, double price, String photoItem) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.photoItem = photoItem;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getPhotoItem() {
        return photoItem;
    }

    public void setPhotoItem(String photoItem) {
        this.photoItem = photoItem;
    }


    public String getPhotoAtIndex(int index) throws ArrayIndexOutOfBoundsException {
        return photoItem;

    }

    public int getCategory() {
        return category;
    }

    public void setCategory(int category) {
        this.category = category;
    }



}
