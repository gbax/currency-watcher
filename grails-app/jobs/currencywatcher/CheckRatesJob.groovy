package currencywatcher

class CheckRatesJob {

    RateService rateService

    static triggers = {
        cron name: 'checkRatesTrigger', cronExpression: "0 0 0 1/1 * ? *"
    }

    def execute() {
        rateService.fillForNewDay(new Date())
    }

}

