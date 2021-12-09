public class Employee {
	
	String employeeID, arbetstitel, fnamn, enamn, stad; 
	double lonPerTim, timVecka;
	int alder;
	boolean ledigDag;
	
	// Anställd utan variabler
	public Employee() {
		this.ledigDag = false;
	}
	
	// Skapa anställd med variabler
	public Employee(String newEmployeeID, String newArbetstitel, String newFnamn, String newEnamn, int newAlder, double newLonPerTim, double newTimVecka, String newStad) {
		this.employeeID = newEmployeeID;
		this.arbetstitel = newArbetstitel;
		this.fnamn = newFnamn;
		this.enamn = newEnamn;
		this.stad = newStad;
		this.alder = newAlder;
		this.lonPerTim = newLonPerTim;
		this.timVecka = newTimVecka;
		this.ledigDag = false;
		
	}
	
	
	// Set och Get funktion för anställda
	public void setLedigDag(boolean newLedigDag) {
		this.ledigDag = newLedigDag;
	}
	
	public boolean getLedigDag() {
		
		return ledigDag;
	}
	
	public void setAlder(int newAlder) {
		this.alder = newAlder;
	}
	
	public int getAlder() {
		
		return alder;
	}
	
	
	public void setLonPerTim(double newLonPerTim) {
		this.lonPerTim = newLonPerTim;
	}
	
	public double getLonPerTim() {
		
		return lonPerTim;
	}
	
	public void setTimVecka(double newTimVecka) {
		this.timVecka= newTimVecka;
	}
	
	public double getTimVecka() {
		
		return timVecka;
	}
	
	public void setEmployeeID(String newEmployeeID) {
		this.employeeID = newEmployeeID;
	}
	
	public String getEmployeeID() {
		
		return employeeID;
	}
	
	public void setArbetstitel(String newArbetstitel) {
		this.arbetstitel = newArbetstitel;
	}
	
	public String getArbetstitel() {
		
		return arbetstitel;
	}
	
	public void setFnamn(String newFnamn) {
		this.fnamn = newFnamn;
	}
	
	public String getFnamn() {
		
		return fnamn;
	}
	
	public void setEnamn(String newEnamn) {
		this.enamn = newEnamn;
	}
	
	public String getEnamn() {
		
		return enamn;
	}
	
	public void setStad(String newStad) {
		this.stad = newStad;
	}
	
	public String getStad() {
		
		return stad;
	}
	
	// Info om anställda till konsollen
	public void printInfo() {
		double ledigDagAvdrag = 0;
		if(ledigDag == true) ledigDagAvdrag = 8;
		System.out.println("EmployID: " + this.employeeID);
		System.out.println("Arbetstitel: " + this.arbetstitel);
		System.out.println("Fnamn: " + this.fnamn);
		System.out.println("Enamn: " + this.enamn);
		System.out.println("Alder: " + this.alder);
		System.out.println("LonPerTim: " + this.lonPerTim);
		System.out.println("TimVecka: " + (this.timVecka - ledigDagAvdrag));
		System.out.println("Stad: " + this.stad);
		System.out.println("");
		
	}
}
