package test;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import logica.test.*;

@RunWith(Suite.class) 
@Suite.SuiteClasses( { 
	TableroTest.class,
	PartidaTest.class,
	UndoTest.class,
	CuatroEnRayaTest.class
	})
public class AllTests {

}