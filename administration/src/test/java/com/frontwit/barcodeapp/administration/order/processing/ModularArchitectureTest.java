package com.frontwit.barcodeapp.administration.order.processing;

import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.junit.ArchUnitRunner;
import com.tngtech.archunit.lang.ArchRule;
import org.junit.runner.RunWith;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;

@RunWith(ArchUnitRunner.class)
@AnalyzeClasses(packages = "com.frontwit.barcodeapp.administration.order.processing")
public class ModularArchitectureTest {

    @ArchTest
    public static final ArchRule shared_should_not_depend_on_others =
            noClasses()
                    .that()
                    .resideInAPackage(".shared..")
                    .should()
                    .dependOnClassesThat()
                    .resideInAnyPackage(".front..", ".order..", ".synchronization..");

    @ArchTest
    public static final ArchRule front_model_should_not_depend_on_others =
            noClasses()
                    .that()
                    .resideInAPackage(".front.model..")
                    .should()
                    .dependOnClassesThat()
                    .resideInAnyPackage(".order..", ".synchronization..");

    @ArchTest
    public static final ArchRule order_model_should_not_depend_on_others =
            noClasses()
                    .that()
                    .resideInAPackage(".order.model..")
                    .should()
                    .dependOnClassesThat()
                    .resideInAnyPackage(".front..", ".synchronization..");

}
