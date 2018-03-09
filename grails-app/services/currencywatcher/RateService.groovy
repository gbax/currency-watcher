package currencywatcher

import groovy.time.TimeCategory

class RateService {

    RatesReceivingService ratesReceivingService

    private static final def storage = new ArrayList<Rate>();

    def getRatesFromDate(Date startDate, Date endDate) {
        if (!startDate || !endDate) {
            throw new NullPointerException("Empty date have came!");
        }
        validateStorage(startDate, endDate);
        return storage
    }

    def validateStorage(Date startDate, Date endDate) {
        def days = null;
        use (TimeCategory) {
            def duration = endDate - startDate
            days = duration.days
        }
        if (storage.size() != days+1) { //+1 because of start day is ignored in duration calc
            storage.clear()
            fillRatesForDate(endDate)
        }
    }

    def fillRatesForDate(Date endDate) {
        Date startDate = null
        Date startDateForQery = null
        use (TimeCategory) {
            startDate = endDate - 1.month
            startDateForQery = startDate - 1.day
        }
        def list = Rate.createCriteria().list() {
          ge('date', startDateForQery.getTime())
        }
        startDate.clearTime();
        startDate.upto(endDate) {
            def unx = it.getTime()
            def find = list.find() { it.date == unx }
            if (find) {
                storage.add(find);
            } else {
                def rate = ratesReceivingService.recievRatesForDate(it)
                def savedRate = save(rate)
                storage.add(savedRate);
            }
        }
    }

    def fillForNewDay(Date endDate) {
        storage.clear()
        fillRatesForDate(endDate)
    }

    def save(def rate) {
        def rawRate = new Rate(date: rate.date.getTime(), usd: rate.usd, eur: rate.eur)
        return rawRate.save();
    }

}
