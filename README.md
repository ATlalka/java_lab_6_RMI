# Java lab 6 - RMI
Aplikacja zrobiona w ramach przedmiotu "Programowanie w języku Java - techniki zaawansowane"  

## Uruchamianie:  
Aby uruchomić program, należy w parametrach uruchomienia programu podać:  
-Djava.security.policy=src/main/resources/policy.policy  
-Djavax.net.ssl.trustStore=src/main/resources/testkeys.jks  
-Djavax.net.ssl.trustStorePassword=pass123  
  
Natomiast w argumentach dla Client i Billborad należy podać odpowiednio:  
*[host] [port] [nazwę szukanej namiastki]*  
  
W moim przypadku jest to:  
*localhost 3000 Manager*

## Treść zadania:

Zaimplementuj rozproszony system imitujący działanie sieci tablic reklamowych, na których cyklicznie wyświetlane są zadane teksty (tj. przez określony czas widać jedno hasło reklamowe, po czym następuje zmiana).
Wymiana danych pomiędzy elementami systemu powinna odbywać się poprzez gniazda SSL (z użyciem certyfikatów), z wykorzystaniem menadżera bezpieczeństwa i plików polityki.
(materiały do przestudiowania: 
 https://docs.oracle.com/javase/8/docs/technotes/guides/rmi/socketfactory/index.html
 https://docs.oracle.com/en/java/javase/11/security/java-secure-socket-extension-jsse-reference-guide.html
)

W systemie tym wyróżnione mają być trzy typy aplikacji (klas z metodą main):
* Manager (Menadżer) - odpowiedzialna za przyjmowanie od klientów zamówień wyświetlanie haseł reklamowych oraz przesyłanie tych haseł na tablice reklamowe
* Client (Klient) - odpowiedzialna za zgłaszanie menadżerowi zamówień lub ich wycofywanie
* Billboard (Tablica) - odpowiedzialna za wyświetlanie haseł, dowiązująca się do menadżera, który może zatrzymać i uruchomić wyświetlanie haseł

Podczas uruchomienia systemu należy utworzyć: 1 instancję Menadżera, przynajmniej 2 instancje Klienta, przynajmniej 3 instancje Tablicy. 
Muszą to być osobne aplikacje (nie mogą korzystać z tej samej przestrzeni adresowej!!!). Aplikacje powinny być parametryzowane na interfejsie lub w linii komend (by dało się je uruchomić na różnych komputerach).

Poniżej znajdują się kody interfejsów oraz kod klasy, które należy użyć we własnej implementacji. Kody te zawierają opisy, które powinny pomóc w zrozumieniu ich zastosowania.
 
Uwaga:
- Proszę uważać na niebezpieczeństwo konfliktu portów.
- Proszę użyć dokładnie tego samego kodu co niżej (bez żadnych modyfikacji!!)

Kody klas:
- IManager
- IClient
- IBillboard
- Order  
zostały dostarczone przez prowadzącego i miały pozostać niemzienione.
