package com.frontwit.barcodeapp.administration;

import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.junit.ArchUnitRunner;
import com.tngtech.archunit.lang.ArchRule;
import org.junit.runner.RunWith;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;

@RunWith(ArchUnitRunner.class)
@AnalyzeClasses(packages = "com.frontwit.barcodeapp.administration")
public class ModularArchitectureTest {

    @ArchTest
    public static final ArchRule commons_should_not_depend_on_synchronization =
            noClasses()
                    .that()
                    .resideInAPackage("..commons..")
                    .should()
                    .dependOnClassesThat()
                    .resideInAPackage("..synchronization..");

    @ArchTest
    public static final ArchRule commons_should_not_depend_on_processing =
            noClasses()
                    .that()
                    .resideInAPackage("..commons..")
                    .should()
                    .dependOnClassesThat()
                    .resideInAPackage("..synchronization..");

    @ArchTest
    public static final ArchRule processing_should_not_depend_on_synchronization =
            noClasses()
                    .that()
                    .resideInAPackage("..processing..")
                    .should()
                    .dependOnClassesThat()
                    .resideInAPackage("..synchronization..");

    @ArchTest
    public static final ArchRule synchronization_should_not_depend_on_processing =
            noClasses()
                    .that()
                    .resideInAPackage("..synchronization..")
                    .should()
                    .dependOnClassesThat()
                    .resideInAPackage("..processing..");
}
