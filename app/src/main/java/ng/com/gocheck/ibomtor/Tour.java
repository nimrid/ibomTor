package ng.com.gocheck.ibomtor;

public class Tour {
    private int mName;
    private int mDescription;
    private int mLocation;
    private int mImageResourceId;
    private int mImageResourceId2;
    private int mImageResourceId3;

    public int getImageResourceId2() {
        return mImageResourceId2;
    }

    public Tour(){
    }

    public Tour(int name, int location, int description, int imageResourceId, int imageResourceId2,
                int imageResourceId3){
        mName = name;
        mLocation = location;
        mDescription = description;
        mImageResourceId = imageResourceId;
        mImageResourceId2 = imageResourceId2;
        mImageResourceId3 = imageResourceId3;
    }

    public int getName() {
        return mName;
    }

    public int getDescription() {
        return mDescription;
    }

    public int getLocation() {
        return mLocation;
    }

    public int getImageResourceId() {
        return mImageResourceId;
    }

    public int getmImageResourceId3() {
        return mImageResourceId3;
    }

    public void setmImageResourceId3(int mImageResourceId3) {
        this.mImageResourceId3 = mImageResourceId3;
    }

    public void setName(int name) {
        mName = name;
    }

    public void setDescription(int description) {
        mDescription = description;
    }

    public void setLocation(int location) {
        mLocation = location;
    }

    public void setImageResourceId(int imageResourceId) {
        mImageResourceId = imageResourceId;
    }
}
