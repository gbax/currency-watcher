package currencywatcher

class Rate {

    Long date;
    Double usd;
    Double eur;

    static constraints = {
        date nullable: false
        usd nullable: false
        eur nullable: false
    }

}
