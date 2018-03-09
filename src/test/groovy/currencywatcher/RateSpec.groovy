package currencywatcher

import grails.testing.gorm.DomainUnitTest
import spock.lang.Specification


class RateSpec extends Specification implements DomainUnitTest<Rate> {

    def setup() {
    }

    def cleanup() {
    }

    void "test Rate validation"() {
        when: 'for nullable eur'
        domain.eur = null

        then: 'eur validation fails'
        !domain.validate(['eur'])
        domain.errors['eur'].code == 'nullable'

        when: 'for nullable usd'
        domain.usd = null

        then: 'usd validation fails'
        !domain.validate(['usd'])
        domain.errors['usd'].code == 'nullable'


        when: 'for nullable usd'
        domain.eur = null

        then: 'usd validation fails'
        !domain.validate(['usd'])
        domain.errors['usd'].code == 'nullable'

    }

    def "testing save method"() {
        when:
        def rate = new Rate(date: 1518202800000, usd: 58.1718, eur: 71.3943)

        then:
        rate.validate()

        and:
        rate.save()

        and: 'there is one additional Rate'
        Rate.count() == old(Rate.count()) + 1
    }
}
