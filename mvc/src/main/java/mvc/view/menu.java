package mvc.view;
import java.util.Scanner;;
public class menu {
    Scanner sc = new Scanner(System.in);
    public void showmenu()
    {
           System.out.println("\n===== MAIN MENU =====");
            System.out.println("1. Student List");
            System.out.println("2. Graduation Readiness Assessment");
            System.out.println("3. Assessment Results");
            System.out.println("0. Exit");
            System.out.print("Select menu: ");
    }

    public int getInt()
    {
        return sc.nextInt();
    }
}
