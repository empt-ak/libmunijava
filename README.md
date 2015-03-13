# libmunijava

## Zadanie
Vytvorte informačný systém pre knižnicu, ktorý bude evidovať jednotlivé knihy v oddeleniach, 
zákazníkov knižnice a jednotlivé pôžičky každého zákazníka. IS by mal viesť evidenciu všetkých 
kníh a zákazníkov. Takisto by mal byť systém schopný viesť evidenciu, ktorý zákazník si aké 
knihy a v akom období vypožičal. Ďalej by mal byť systém schopný zaznamenávať údaje, ktorú 
knihu mali ktorí zákazníci vypožičanú a v akom stave ju vrátili. Počítajte s tým, že jeden 
zákazník si na jednu pôžičku môže požičať viac kníh.


## Štruktúra projektu

  * **library_api** - modul definujúci rozhranie
  * **library_backend**  - modul slúžiaci pre prístup k databázy a konverziu na DTO objekty, 
  implementujúci rozhranie z **library_api**
  * **library_client_gui**  - modul obsahujúci grafické užívateľské rozhranie, používajúce modul **library_soapapi**
  * **library_server**  - modul obsahujúci aplikačný server, webové stránky a webové služby
  * **library_soapapi**  - modul, ktorý poskytuje webove služby implementované v module **library_server**
  
## Návod na spustenie

  * Stiahneme projekt z githubu pomocou príkazu **git clone https://github.com/empt-ak/libmunijava.git**
  * alebo ak už projekt máme stiahnutý updateneme zdrojové kódy pomocou **git pull**
  * V rodičovskom projekte príkazom **mvn clean install** zbuildujeme aplikáciu vrátane spustenia jednotkových testov
  * Následne spustíme server následovne:
    * prejdeme do zložky **library_server** príkazom **cd library_serverr**
    * spustíme príkazom **mvn tomcat7:run** aplikačný server
    * server beží ak výpis v konzole zastane na riadku *INFO: Starting ProtocolHandler ["http-bio-8080"]*
    * na adrese *http://localhost:8080/pa165* sú následne dostupné webové stránky
  * Z druhého okna terminálu / príkazovej riadky spustíme clienta s grafickým rozhraním
    * prejdeme do zložky *library_client_gui* príkazom **cd library_client_gui**
    * spustíme klienta príkazom **mvn exec:java**
  * V oboch verziách rozhrania majú členovia tímu vlastný účet + prihlasovacie heslo. Náhodný užívateľ môže použiť východzie prihlasovacie meno **admin** s heslom **admin**
  * Ak chceme dokumentáciu z rodičovského projektu príkazom **mvn javadoc:aggregate** vygenerujeme dokumentáciu
  
    
