


public class Person{


    private String name;
    private int age;
    private List<String> interests = new ArrayList<>();
    private String bio;


    public void setName(String name){
        this.name = name;
    }

    public void setAge(int age){
        this.age = age;
    }

    public void setInterests(List<String> interests){
        this.interests = interests;
    }

    public void setBio(String bio){
        this.bio = bio;
    }

    public String getName(){
        return name;
    }

    public int getAge(){
        return age;
    }
    public List<String> getInterests(){
        return interests;
    }

    public String getBio(){
        return Bio;
    }

    public void addInterests(String newInterest){
        interests.add(newInterest);
    }
    
    public void removeInterest(String removeInterest){
        for (int i = 0; i < interest.size(); i++){
            if(interests.get(i).equals(removeInterest)){
                int index = i;

            }
        }
        interest.remove(index);
    }


public static void main(String[] args){
    Person Thea = new Person();
    Thea.setName("Thea");
    System.out.println(Thea.getName());
}


    
    
    }