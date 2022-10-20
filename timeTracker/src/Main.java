public class Main {
    public static void main(String[] args) {

        Project p1 = new Project("p1",null);
        Task t1 = new Task("t1", p1);

        p1.printName();

        System.out.print("\n");

        p1.whoAmI();

        t1.printName();

        System.out.print("\n");

        t1.whoAmI();



    }
}