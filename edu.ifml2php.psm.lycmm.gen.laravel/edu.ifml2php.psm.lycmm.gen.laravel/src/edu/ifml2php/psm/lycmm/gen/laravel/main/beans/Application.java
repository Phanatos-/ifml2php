package edu.ifml2php.psm.lycmm.gen.laravel.main.beans;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import edu.ifml2php.psm.lycmm.gen.laravel.services.FileReader;
import edu.ifml2php.psm.lycmm.gen.laravel.services.LaravelInputPaths;

public class Application {
	//Hace falta una clase de Paths? Cada path pertenece a un "concern" y quiza pueda asignarse en la clase correspondiente.
	//Lo vamos a hacer así porque en caso de que sea ncesario cambiar las rutas por un cambio de versión de Laravel podemos decir que usamos el principio de responsasbilidad simple
	
   public String locale = "es-ES";
   private FileReader fr = new FileReader();
   private LaravelInputPaths lip = new LaravelInputPaths();
   
   public String getDefaultAppName(String framework) {
	   switch(framework) {
	      case "Laravel": 
	    	  return "App";
	      case "Yii2":
	    	  return "";
	      case "CI":
	    	  return "";
	      default:
	    	  return "";
	   }
   }
   
	public String getAppConfigLines(String keyword) {
      return fr.getFile(lip.getConfigAppPath(), keyword);
    }
	
	public String getAppConfig() {
		String baseDir = lip.getBaseDir();
		String configAppPath = lip.getConfigAppPath();
		String file = fr.getFile(baseDir + configAppPath);
		return file;
	}
	
	public String getRoutes(){
		return fr.getFile(lip.getBaseDir() + lip.getRoutesPath());
	}
		 
	public String getLocale() {
		return locale;
	}
	
	public void setLocale(String locale) {
		this.locale = locale;
	}
	
	public void setAppName(String pathFramework, String appName) {
		Runtime r = Runtime.getRuntime();
		Process p;
		try {
			p = r.exec("php " + pathFramework + "/artisan app:name " + appName);
			p.waitFor();
			BufferedReader b = new BufferedReader(new InputStreamReader(p.getInputStream()));
			String line = "";
			
			while ((line = b.readLine()) != null) {
				  System.out.println(line);
			}
			
			b.close();
			
		} catch (IOException | InterruptedException e) {
			e.printStackTrace();
		}
	}
	
}
