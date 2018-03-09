package currencywatcher

class BootStrap {

    RateService rateService

    def init = { servletContext ->
       rateService.fillRatesForDate(new Date())
    }

    def destroy = {
    }
}
