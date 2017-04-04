package pl.potera.bmiapp

import spock.lang.Specification
import spock.lang.Subject

class ClientTest extends Specification{
    static final float NORMAL_MASS = 80
    static final float NORMAL_HEIGHT = 1.8

    @Subject
    CountBmi uut

    def setup(){
        uut = new CountBmiKG()
    }

    def "isMassValid should return true for valid mass"(){
        when:
        def result = uut.isMassValid(NORMAL_MASS)

        then:
        result
    }

    def "isMassValid should return false when mass is out of bounds"(){
        given:
        def smallMass = 0
        def bigMass = 300

        when:
        def smallMassResult = uut.isMassValid(smallMass)
        def bigMassResult = uut.isMassValid(bigMass)

        then:
        !smallMassResult
        !bigMassResult
    }

    def "isHeightValid should return true for valid height"(){
        when:
        def result = uut.isHeightValid(NORMAL_HEIGHT)

        then:
        result
    }

    def "isHeightValid should return false when height is out of bounds"(){
        given:
        def smallHeight = 0
        def bigHeight = 3

        when:
        def smallHeightResult = uut.isHeightValid(smallHeight)
        def bigHeightResult = uut.isHeightValid(bigHeight)

        then:
        !smallHeightResult
        !bigHeightResult
    }

    def "countBmi should return valid result"(){
        when:
        uut.countBmi(NORMAL_MASS, NORMAL_HEIGHT)

        then:
        noExceptionThrown()
    }

    def "countBmi should throw IllegalArgumentException for invalid arguments"(){
        given:
        def smallMass = 0

        when:
        uut.countBmi(smallMass, NORMAL_HEIGHT)

        then:
        thrown(IllegalArgumentException)
    }
}
