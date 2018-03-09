package currencywatcher

import grails.testing.mixin.integration.Integration
import grails.testing.services.ServiceUnitTest
import groovy.time.TimeCategory
import spock.lang.Specification

@Integration
class RateServiceSpec extends Specification implements ServiceUnitTest<RateService>{

    void "Test recieving data"() {
        given:
        def endDate = new Date();
        def startDate = null
        use (TimeCategory) {
            startDate = endDate - 1.month
        }
        when:
        def result = service.getRatesFromDate(startDate, endDate)
        then:
        result.size() != null
        Rate rate = result.get(0)
        rate.getDate() != null
        rate.getUsd() != null
        rate.getEur() != null

    }
}
