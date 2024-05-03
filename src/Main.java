public class Main {
    public static void main(String[] args) {
        Data data = new Data();
        data.loadData();

        // testing students
        for (Student s : data.getStudents()) {
            System.out.println(s);
        }
    }
}