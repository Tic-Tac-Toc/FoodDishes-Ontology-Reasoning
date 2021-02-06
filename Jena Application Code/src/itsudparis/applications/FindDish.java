package itsudparis.applications;

import com.hp.hpl.jena.rdf.model.Model;
import itsudparis.tools.JenaEngine;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class FindDish {
    public static String inputDataOntology = "data/DishesAndIngredients.owl";
    public static String inputRule = "data/rules/rules.txt";
    public static String getDishQuery = "data/queries/getDish.txt";

    /**
     * @param args rhe command line arguments
     */

    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        Model model = JenaEngine.readModel(inputDataOntology);
        Model inferedModel = JenaEngine.readInferencedModelFromRuleFile(model, inputRule);

        while (true) {
            System.out.println("Sur quel plat voulez-vous avoir des informations ?");
            // Reading data using readLine
            String name = reader.readLine();
            if (name.equals("stop"))
                break;

            String results = JenaEngine.executeQueryFileWithParameter(inferedModel, getDishQuery, name);
            if (results.contains(("ns:"))) {
                System.out.println("Informations :");
                System.out.println(results);
            }
            else {
                System.out.println(("Ce plat ne fait pas partie de notre base de données."));
            }
        }
    }
}