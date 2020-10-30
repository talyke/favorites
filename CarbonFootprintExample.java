//Tara Keene
//01.24.17
//CSIS 1410
//HW no.2 v.1

//simple Java carbon footprint calculator program

public class CarbonFootprintExample

   public static void Main(string[] args)
   {
      ICarbonFootprint[] list = new ICarbonFootprint[3];
   
      // add elements to list
      list[0] = new Bicycle();
      list[1] = new Building(2500);
      list[2] = new Car(10);
   
      // display carbon footprint of each object
      for (int i = 0; i < list.Length; i++)
         list[i].GetCarbonFootprint();
   } // end Main
}
