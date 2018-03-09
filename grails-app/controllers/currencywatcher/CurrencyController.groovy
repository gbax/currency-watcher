package currencywatcher

import grails.converters.JSON
import groovy.time.TimeCategory

class CurrencyController {

    RatesReceivingService ratesReceivingService
    RateService rateService

    static allowedMethods = [list: 'GET']

    def 'month-rates'() {
        def endDate = new Date();
        def startDate = null
        use (TimeCategory) {
            startDate = endDate - 1.month
        }
        def list = rateService.getRatesFromDate(startDate, endDate)
        render list as JSON
    }

}
