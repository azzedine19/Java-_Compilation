package testSqlite02.orm;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "utilisateurs")
public class Utilisateur {
    
    @DatabaseField(id = true)
    public String nom;
    
    @DatabaseField
    public String password;
    
    public Utilisateur() {
        // Un constructeur sans paramètre est obligatoire pour ORMLite
    }
    public Utilisateur(String name, String password) {
        this.nom = name;
        this.password = password;
    }
    
}
