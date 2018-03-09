package currencywatcher

import org.cyberneko.html.parsers.SAXParser

class RatesReceivingService {

    def grailsApplication

    def recievRatesForDate(Date date) {
        if (!date) {
            throw new NullPointerException("Empty date have came!");
        }
        String targetRatesUrl = grailsApplication.config.getProperty('target.rates.url')
        if (!targetRatesUrl) {
            throw new NullPointerException("Target url doesn't set!");
        }
        def dateAsString = date.format("dd.MM.yyyy");

        def parser = new SAXParser()

        def page = new XmlSlurper(parser).parse String.format(targetRatesUrl, dateAsString)

        def usdString = page.depthFirst().find {
            it.text() == 'USD'
        }.parent().childNodes().find {
            it.text().contains(',')
        }.text();

        def eurString = page.depthFirst().find {
            it.text() == 'EUR'
        }.parent().childNodes().find {
            it.text().contains(',')
        }.text()

        if (!eurString || !eurString) {
            throw new Exception("There is no rates!")
        }

        def usd = Double.parseDouble(usdString.replaceAll(",", "."))
        def eur = Double.parseDouble(eurString.replaceAll(",", "."))

        return [date: date, usd: usd, eur: eur]
    }

}
