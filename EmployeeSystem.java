import java.util.*;

public class EmployeeSystem {
	public static void main(String [] args) {
		EmployeeSystem database = new EmployeeSystem();
		Employee[] employees = database.initialise(); // Initialisera
		Scanner keyboard = new Scanner(System.in); // Skapa Scanner objekt
		String choice = "";

		// Meny alternativ
		
		System.out.println("Welcome to the Employee System!");
		System.out.println("These are your options:");
		System.out.println("a: Add employee to system."); 
		System.out.println("b: Update title based on ID.");
		System.out.println("c: Remove employee based on ID.");
		System.out.println("d: Show info about searched employee.");
		System.out.println("e: Calculate wage for one weeks work.");
		System.out.println("f: Show employees by searched title.");
		System.out.println("g: Employee count by title.");
		System.out.println("h: Show all employees.");
		System.out.println("i: Show employees below wage.");
		System.out.println("j: Give random day off.");
		System.out.println("k: Sort all employees by title.");
		System.out.println("q: Quit.");
		System.out.println("Enter a letter from a-k or q to quit.");

		// Anv�ndarinput
		
		while (!choice.equals("q") && !choice.equals("Q")) { // Medans valet inte �r "q" g�r n�got annat
			choice = database.getString(keyboard);

			System.out.println("You selected option " + choice); // Visar anv�ndarens val

			if(choice.toLowerCase().equals("a")) { // Val f�r anv�ndaren att l�gga till en anst�lld i systemet
				database.addEmployeetoArray(employees, database.addEmployee(keyboard));

			}
			else if (choice.toLowerCase().equals("b")) { // Uppdatera titel hos en anst�lld
				Employee employee = database.searchEmployeeID(employees, keyboard);
				if(employee != null) {
					employee = database.updateTitle(employee, keyboard);
				}

			}
			else if (choice.toLowerCase().equals("c")) { // Ta bort anst�lld via employeeID
				database.deleteEmployee(employees, keyboard);

			}

			else if (choice.toLowerCase().equals("d")) { // Visa information om anst�lld
				Employee employee = database.searchEmployeeID(employees, keyboard);
				if(employee != null) employee.printInfo();

			}

			else if (choice.toLowerCase().equals("e")) { // Ber�kna och visa l�n/v f�r s�kt anst�lld
				Employee employee = database.searchEmployeeID(employees, keyboard);
				if(employee != null) database.weeklyPay(employee);

			}

			else if (choice.toLowerCase().equals("f")) { // Lista alla anst�llda med s�kt titel
				System.out.println("Enter title: ");
				database.searchArbetstitel(employees, keyboard);

			}

			else if (choice.toLowerCase().equals("g")) { // Summa av antalet anst�llda baserat p� titel
				System.out.println("Enter title: ");
				System.out.println(database.countArbetstitel(employees, keyboard));

			}

			else if (choice.toLowerCase().equals("h")) { // Lista alla anst�llda
				database.printAll(employees);

			}

			else if (choice.toLowerCase().equals("i")) { // S�k efter anst�llda med l�gre l�n �n angivet
				System.out.println("Enter wage: ");
				database.lowerWages(employees, keyboard);
				keyboard.nextLine();

			}

			else if (choice.toLowerCase().equals("j")) { // Slumpa fram en anst�lld som vinner en ledig dag	
				Employee[] tempEmployees = database.getAboveHours(employees);
				Employee tempEmployee = database.ledigDag(tempEmployees);
				database.updateEmployee(employees, tempEmployee);
				System.out.println("Day off given to:");
				tempEmployee.printInfo();

			}

			else if (choice.toLowerCase().equals("k")) { // Sorterar alla anst�llda via titel
				database.sortArbetstitel(employees);
				database.printAll(employees);

			}

			else if (choice.toLowerCase().equals("q")) { // St�ng programmet
				System.out.println("Goodbye!");
				System.exit(0);
			}

			else {
				System.out.println("Invalid option.");
			}
			System.out.println("Would u like to do something else? \nEnter a letter from a-k or q to quit."); // Fr�gar om anv�ndaren vill g�ra n�got annat/mer
		}
	}

	
	// a. L�gg till anst�lld
	/* 
	 * @Param Scanner f�r anv�ndar input
	 * @Return/F� tillbaka anst�lld
	 * Ber om information om den nya anst�llda via fr�gor
	 */
	
	public Employee addEmployee(Scanner keyboard) {

		Employee employee = new Employee(); // Skapa employee objekt
		System.out.println("Enter employee ID: "); // L�gg till anst�llnings ID
		String id = getString(keyboard);
		employee.setEmployeeID(id);

		System.out.println("Enter title: "); // L�gg till arbetstitel
		String title = getString(keyboard);
		employee.setArbetstitel(title);

		System.out.println("Enter Firstname: "); // L�gg till f�rnamn
		String firstname = getString(keyboard);
		employee.setFnamn(firstname);

		System.out.println("Enter Lastname: "); // L�gg till efternamn
		String lastname = getString(keyboard);
		employee.setEnamn(lastname);

		System.out.println("Enter Age: "); // L�gg till �lder
		int age = getInt(keyboard);
		employee.setAlder(age);

		System.out.println("Enter Pay: "); // L�gg till l�n
		double pay = getDouble(keyboard);
		employee.setLonPerTim(pay);

		System.out.println("Enter workhours: "); // L�gg till arbetstimmar
		double workhours = getDouble(keyboard);
		employee.setTimVecka(workhours);

		keyboard.nextLine();
		System.out.println("Enter City: "); // L�gg till stad
		String city = getString(keyboard);
		employee.setStad(city);		

		return employee; // F� tillbaka employee objekt
	}


	/*
	 * @Param Employee[] en array av Employees som inneh�ller objekten employee; newEmployee �r ett Employee objekt som kommer l�ggas till till arrayen av Employees
	 * L�gg till employee till arrayen ifall person med samma ID inte redan finns 
	 */
	
	public void addEmployeetoArray(Employee employees[], Employee newEmployee) {
		for(int i = 0; i< employees.length; i++) { // Loopa genom arrayen av employees till slutet
			if(employees[i] == null) { // Null check 
				employees[i] = newEmployee; // newEmployee tillagd till arrayen
				System.out.println(employees[i].employeeID + " added");
				return;
			}// Meddelande ifall person redan finns
			if(employees[i].employeeID.equals(newEmployee.employeeID)) { // Kollar om person med samma ID redan finns med i arrayen
				System.out.println("A person with this ID is already in your system");
				return; 
			}
		}
	}

	/*
	 * @Param Employee[] en array av Employees som inneh�ller objekten Employee, Scanner f�r anv�ndar input
	 * @Return f� tillbaka den s�kta anst�llda
	 * S�ker efter Employee i Employee arrayen
	 */
	
	public Employee searchEmployeeID(Employee employees[], Scanner keyboard) {
		System.out.println("Enter employeeID:");
		String employeeID = getString(keyboard); // S�k person via employeeID
		for(int i = 0; i < employees.length; i++) {
			if(employees[i] == null ) { 
				System.out.println("Employee does not exist");
				return null;
			}
			if(employeeID.equals(employees[i].employeeID)) {
				return employees[i];
			}
		}
		System.out.println("Employee does not exist");
		return null;
	}

	
	// b. Updatera arbetstitel baserat p� employeeID
	/*
	 * @Param Employee[] en array av Employees som inneh�ller objekt employee
	 * Kolla om Employee �r i arrayen och uppdatera informationen
	 */
	
	public void updateEmployee(Employee employees[], Employee employee) {

		for(int i = 0; i < employees.length; i++) {
			if(employees[i] == null) { 
				System.out.println("Employee does not exist");
				return;
			}
			if(employee.employeeID.equals(employees[i].employeeID)) { // Ifall anst�lld hittas uppdatera anst�lld
				employees[i] = employee;
				return;
			}
		}
	}

	/*
	 * @Param Employee[] en array av Employees som inneh�ller objekten Employee, Scanner f�r anv�ndar input
	 * @Return/F� tillbaka uppdaterad anst�lld
	 * Uppdaterar Employee i Employee arrayen
	 */
	
	public Employee updateTitle(Employee employee, Scanner keyboard) {
		System.out.println("What is the new tite of the employee"); // L�gg in ny titel
		String newArbetstitel = getString(keyboard);
		employee.setArbetstitel(newArbetstitel);
		return employee;
	}	

	
	// c. Ta bort anst�lld baserat p� employeeID
	/*
	 * @Param Employee [] en array av Employees som inneh�ller objekten Employee, Scanner f�r anv�ndar input
	 * Ta bort anst�lld om s�kt anst�lld fanns med i arrayen
	 */
	
	public void deleteEmployee(Employee employees[], Scanner keyboard) {
		Employee deleteEmployee = searchEmployeeID(employees, keyboard);
		if(deleteEmployee == null) return;
		else {
			for(int i = 0; i<employees.length; i++) {
				if(employees[i].employeeID.equals(deleteEmployee.employeeID)) { // Kolla om ID matchar i arrayen
					System.out.println(employees[i].employeeID + " was removed");
					employees[i] = null; 
					moveEmployee(employees, i);
					return;
				}
			}
		}
	}
	
	// Flyttar Employees position till v�nster
	/*
	 * @Param Employee [] en array av Employees som inneh�ller objekten Employee
	 */
	
	public void moveEmployee(Employee employees[], int i) {
		while(i<employees.length) {
			if(employees[i+1] == null) break;
			employees[i] = employees[i+1];
			employees[i+1] = null;
			i++;
		}
	}


	// e. R�kna ut l�nen f�r en veckas arbete
	/*
	 * @Param Employee [] en array av Employees som inneh�ller objekten Employee
	 * Kalkulerar veckol�nen och visar anv�ndar ID, f�r- och efternamn samt veckol�n i konsollen
	 */
	
	public void weeklyPay(Employee employee) {
		System.out.println("Employee ID: " + employee.employeeID);
		System.out.println("Namn: " + employee.fnamn + " " + employee.enamn);
		System.out.println("L�n: " + (employee.lonPerTim*employee.timVecka));  // Timl�n g�nger antalet arbetstimmar

	}

	// f. Lista alla arbetare med s�kt titel
	/*
	 * @Param Employee [] en array av Employees som inneh�ller objekten Employee, Scanner f�r anv�ndar input
	 * S�ker och visar alla anst�llda under angiven titel
	 */

	public void searchArbetstitel(Employee employees[], Scanner keyboard) {
		String arbetstitel = getString(keyboard);
		int count = 0;
		for(int i = 0; i<employees.length; i++) {
			if(employees[i] == null) {
				if(count == 0) System.out.println("No employee has this title");
				return;
			}
			if(employees[i].arbetstitel.equals(arbetstitel)) {
				employees[i].printInfo();
				count++;
			}
		}
	}

	// g. R�kna alla med samma arbetstitel
	/*
	 * @Param Employee [] en array av Employees som inneh�ller objekten Employee, Scanner f�r anv�ndar input
	 * @Return Antalet med samma arbetstitel
	 * R�knar alla anst�llda med samma titel
	 */
	
	public int countArbetstitel(Employee employees[], Scanner keyboard) {
		String arbetstitel = getString(keyboard);
		int count = 0;
		for(int i = 0; i<employees.length; i++) {
			if(employees[i] == null) break;
			if(employees[i].arbetstitel.equals(arbetstitel)) {
				count++;
			}
		}
		return count;
	} 


	// h. Info om alla arbetare
	/*
	 * @Param Employee [] en array av Employees som inneh�ller objekten Employee
	 * Visa information om alla anst�llda i konsollen
	 */

	public void printAll(Employee[] employees) {

		for(int i = 0; i<employees.length; i++) {
			if(employees[i] == null) break;

			employees[i].printInfo();
		}
	}


	// i. S�k under specifik l�n
	/*
	 * @Param Employee [] en array av Employees som inneh�ller objekten Employee, Scanner f�r anv�ndar input
	 * Visa alla anst�llda med en l�n l�gre �n vad som angivits
	 */

	public void lowerWages(Employee[] employees, Scanner keyboard) {
		double wage = getDouble(keyboard);
		for(int i = 0; i<employees.length; i++) {
			if(employees[i] == null) break;
			if(employees[i].lonPerTim<wage) {
				System.out.println("Name: " + employees[i].fnamn + " " + employees[i].enamn);

			}
		}
	}
	
	// j. Anst�lld �ver 30h/vecka
	/*
	 * @Param Employee [] en array av Employees som inneh�ller objekten Employee
	 * @Return array av anst�llda med mer �n 30h/vecka
	 * Skapar en array med anst�llda som jobbar mer �n 30h/vecka
	 */
	
	public Employee[] getAboveHours(Employee[] employees) {
		Employee[] aboveEmployee = new Employee[20];
		int counter = 0;
		for(int i = 0; i<employees.length; i++) {
			if(employees[i] == null) break;
			if(employees[i].timVecka>30) {
				aboveEmployee[counter] = employees[i];
				counter++;
			}
		}
		return aboveEmployee;
	}

	// Random person
	/*
	 * @Param Employee [] en array av Employees som inneh�ller objekten Employee
	 * @Return person som f�r en ledig dag
	 * Slumpar ut en anst�lld som kan f� en ledig dag baserat p� att jobba minst 30h/vecka
	 */
	
	public Employee ledigDag(Employee[] employees) {
		Random r = new Random();
		int counter = 0;
		for(int i = 0; i< employees.length; i++) {
			if(employees[i] == null) break;
			counter++;
		}
		int ledigDag = r.nextInt(counter);
		employees[ledigDag].setLedigDag(true);
		return employees[ledigDag];
	}


	// k. Sortera baserat p� arbetstitel
	/*
	 * @Param Employee [] en array av Employees som inneh�ller objekten Employee
	 * Sorterar alla anst�llda baserat p� arbetstitel
	 */
	
	public void sortArbetstitel(Employee[] employees){
		Employee temp;
		boolean sorted = false;
		while(!sorted) {
			sorted = true;
			for(int i = 0; i<employees.length; i++) {
				if(employees[i] == null) break;
				if(employees[i+1] == null) break;
				if(0 <(employees[i].arbetstitel).toLowerCase().compareTo(employees[i+1].arbetstitel.toLowerCase())) {
					temp = employees[i]; // H�ller temp
					employees[i] = employees[i+1]; // Byt till v�nster
					employees[i+1] = temp; // Byt till h�ger
					sorted = false;
				}
			}
		}
	}
	
	/*
	 * @Return En array med anst�llda som redan initialiserats
	 * Initialiserar start/f�rbest�md data f�r anst�llda
	 */
	
	public Employee[] initialise() {
		Employee[] employees = new Employee[20];
		employees[0] = new Employee("E1", "Snickare", "Hanna", "Bell", 22, 180, 40, "Falun");
		employees[1] = new Employee("E2", "Systemutvecklare", "Lina", "Moraberg", 29, 220, 35, "Gagnef");
		employees[2] = new Employee("E3", "Radiopratare", "Helge", "Skolhage", 58, 200, 20, "Ludvika");
		return employees;
	}


	/*
	 * @Param Scanner f�r anv�ndar input
	 * @Return/f� tillbaka ett string v�rde
	 * H�mta anv�ndar input som string och f� tillbaka v�rde
	 * H�mtar input som string och ge tillbaka v�rde om giltigt
	 */
	public String getString(Scanner keyboard) {
		try {
			String word = keyboard.nextLine(); // H�mta string input fr�n anv�ndaren
			return word; // F� tillbaka input
		}
		catch(InputMismatchException e) {
			System.out.println("Invalid input"); // Ogiltig String - error
			return getString(keyboard);
		}

	}

	/*
	 * @Param Scanner f�r anv�ndar input
	 * @Return/f� tillbaka double v�rde
	 * H�mtar input f�r double och ge tillbaka v�rde om giltigt
	 */
	public double getDouble(Scanner keyboard) {
		try {
			double num = keyboard.nextDouble();// H�mta double fr�n anv�ndaren
			if(num < 0) {
				System.out.println("Cant be a negative number");
				keyboard.nextLine();
				return getDouble(keyboard);
			}
			return num; // Return/f� tillbaka Double v�rde fr�n anv�ndaren
		}

		catch(InputMismatchException e) {
			System.out.println("Please enter a valid number");// Ogiltigt double v�rde - error
			keyboard.nextLine();
			return getDouble(keyboard);
		}
	}
	
	/*
	 * @Param Scanner f�r anv�ndar input
	 * @Return/f� tillbaka int v�rde
	 * H�mtar input f�r int och ge tillbaka v�rde om giltigt
	 */
	public int getInt(Scanner keyboard) {
		try {
			int num = keyboard.nextInt();// H�mta int fr�n anv�ndaren
			if(num < 0) {
				System.out.println("Cant be a negative number");
				keyboard.nextLine();
				return getInt(keyboard);
			}
			return num; // Return/f� tillbaka int fr�n anv�ndaren
		}

		catch(InputMismatchException e) {
			System.out.println("Non valid number");// Ogiltigt int v�rde - error
			keyboard.nextLine();
			return getInt(keyboard);
		}
	}

}
