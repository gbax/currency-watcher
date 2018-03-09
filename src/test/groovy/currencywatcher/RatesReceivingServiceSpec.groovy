package currencywatcher

import grails.testing.services.ServiceUnitTest
import spock.lang.Specification


class RatesReceivingServiceSpec extends Specification implements ServiceUnitTest<RatesReceivingService> {

    void "Recieve page from target url and parse correct"() {
        given:
        def date = new Date()
        when:
        def result = service.recievRatesForDate(date);
        then:
        result != null
        result.date != null
        result.usd != null
        result.eur != null
    }
}
