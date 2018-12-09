package statistics;

import statistics.matcher.*;

public class Querybuilder {

    Matcher m;

    public Querybuilder() {
        m = new All();
    }

    public Matcher build() {
        Matcher m2 = this.m;
        this.m = new All();
        return m2;
    }

    public Querybuilder playsIn(String team) {
        this.m = new And(this.m, new PlaysIn(team));
        return this;
    }

    public Querybuilder hasFewerThan(int value, String category) {
        this.m = new And(this.m, new HasFewerThan(value, category));
        return this;
    }

    public Querybuilder hasAtLeast(int value, String category) {
        this.m = new And(this.m, new HasAtLeast(value, category));
        return this;
    }

    public Querybuilder and(Matcher... matchers) {
        this.m = new And(this.m, new And(matchers));
        return this;
    }

    public Querybuilder or(Matcher... matchers) {
        this.m = new And(this.m, new Or(matchers));
        return this;
    }

    public Querybuilder oneOf(Matcher... matchers) {
        this.m = new And(this.m, new Or(matchers));
        return this;
    }
}