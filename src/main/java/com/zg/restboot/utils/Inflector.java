package com.zg.restboot.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 英文单词单复数转换
 */
public class Inflector {
    private static final Pattern UNDERSCORE_PATTERN_1 = Pattern.compile("([A-Z]+)([A-Z][a-z])");
    private static final Pattern UNDERSCORE_PATTERN_2 = Pattern.compile("([a-z\\d])([A-Z])");

    private static final List<RuleAndReplacement> plurals = new ArrayList<>();
    private static final List<RuleAndReplacement> singulars = new ArrayList<>();
    private static final List<String> uncountables = new ArrayList<>();

    private static Inflector instance;

    private Inflector() {
        initialize();
    }

    public static void main(String[] args) {
        // 单数转复数
        System.out.println(Inflector.pluralize("water"));
        System.out.println(Inflector.pluralize("box"));
        System.out.println(Inflector.pluralize("tomato"));
        System.out.println(Inflector.pluralize("user"));
        System.out.println(Inflector.pluralize("company"));
        System.out.println("aagquiz".replaceAll("(quiz)$", "$1zes"));
        System.out.println("aagox".replaceAll("(ox)$", "$1es"));
        System.out.println("amouse".replaceAll("([m|l])ouse$", "$1ice"));
        System.out.println("apex".replaceAll("(matr|vert|ind)ix|ex$", "$1ices"));
        System.out.println("cash".replaceAll("(x|ch|ss|sh)$", "$1es"));
        System.out.println("series".replaceAll("([^aeiouy]|qu)ies$", "$1y"));
        System.out.println("busy".replaceAll("([^aeiouy]|qu)y$", "$1ies"));
        System.out.println("hive".replaceAll("(hive)$", "$1s"));
        System.out.println("self".replaceAll("(?:([^f])fe|([lr])f)$", "$1$2ves"));
        System.out.println("analysis".replaceAll("sis$", "ses"));
        System.out.println("musium".replaceAll("([ti])um$", "$1a"));
        System.out.println("atomato".replaceAll("(buffal|tomat)o$", "$1oes"));
        System.out.println("keybus".replaceAll("(bu)s$", "$1es"));
        System.out.println("alias".replaceAll("(alias|status)$", "$1es"));
        System.out.println("virus".replaceAll("(octop|vir)us$", "$1i"));
        System.out.println("adjustis".replaceAll("(ax|test)is$", "$1es"));
    }
    private void initialize() {
        plural("$", "s");
        plural("s$", "s");
        plural("(ax|test)is$", "$1es");
        plural("(octop|vir)us$", "$1i");
        plural("(alias|status)$", "$1es");
        plural("(bu)s$", "$1es");
        plural("(buffal|tomat)o$", "$1oes");
        plural("([ti])um$", "$1a");
        plural("sis$", "ses");
        plural("(?:([^f])fe|([lr])f)$", "$1$2ves");
        plural("(hive)$", "$1s");
        plural("([^aeiouy]|qu)y$", "$1ies");
        plural("([^aeiouy]|qu)ies$", "$1y");
        plural("(x|ch|ss|sh)$", "$1es");
        plural("(matr|vert|ind)ix|ex$", "$1ices");
        plural("([m|l])ouse$", "$1ice");
        plural("(ox)$", "$1es");
        plural("(quiz)$", "$1zes");

        singular("s$", "");
        singular("(n)ews$", "$1ews");
        singular("([ti])a$", "$1um");
        singular("((a)naly|(b)a|(d)iagno|(p)arenthe|(p)rogno|(s)ynop|(t)he)ses$", "$1$2sis");
        singular("(^analy)ses$", "$1sis");
        singular("([^f])ves$", "$1fe");
        singular("(hive)s$", "$1");
        singular("(tive)s$", "$1");
        singular("([lr])ves$", "$1f");
        singular("([^aeiouy]|qu)ies$", "$1y");
        singular("(s)eries$", "$1eries");
        singular("(m)ovies$", "$1ovie");
        singular("(x|ch|ss|sh)es$", "$1");
        singular("([m|l])ice$", "$1ouse");
        singular("(bus)es$", "$1");
        singular("(o)es$", "$1");
        singular("(shoe)s$", "$1");
        singular("(cris|ax|test)es$", "$1is");
        singular("([octop|vir])i$", "$1us");
        singular("(alias|status)es$", "$1");
        singular("^(ox)es", "$1");
        singular("(vert|ind)ices$", "$1ex");
        singular("(matr)ices$", "$1ix");
        singular("(quiz)zes$", "$1");

        irregular("person", "people");
        irregular("man", "men");
        irregular("child", "children");
        irregular("sex", "sexes");
        irregular("move", "moves");

        uncountable("equipment", "information", "rice", "money", "species", "series", "fish", "sheep");
    }

    public static Inflector getInstance() {
        if (instance == null) {
            instance = new Inflector();
        }
        return instance;
    }

    public static String underscore(String camelCasedWord) {
        String underscoredWord = UNDERSCORE_PATTERN_1.matcher(camelCasedWord).replaceAll("$1_$2");
        underscoredWord = UNDERSCORE_PATTERN_2.matcher(underscoredWord).replaceAll("$1_$2");
        underscoredWord = underscoredWord.replace('-', '_').toLowerCase();

        return underscoredWord;
    }

    public static String pluralize(String word) {
        if (uncountables.contains(word.toLowerCase())) {
            return word;
        }
        return replaceWithFirstRule(word, plurals);
    }

    public String singularize(String word) {
        if (uncountables.contains(word.toLowerCase())) {
            return word;
        }
        return replaceWithFirstRule(word, singulars);
    }

    private static String replaceWithFirstRule(String word, List<RuleAndReplacement> ruleAndReplacements) {

        for (RuleAndReplacement rar : ruleAndReplacements) {
            String rule = rar.getRule();
            String replacement = rar.getReplacement();

            // Return if we find a match.
            Matcher matcher = Pattern.compile(rule, Pattern.CASE_INSENSITIVE).matcher(word);
            if (matcher.find()) {
                return matcher.replaceAll(replacement);
            }
        }
        return word;
    }

    public static String tableize(String className) {
        return pluralize(underscore(className));
    }

    public static String tableize(Class<?> klass) {
        String className = klass.getName().replace(klass.getPackage().getName() + ".", "");
        return tableize(className);
    }

    private static void plural(String rule, String replacement) {
        plurals.add(0, new RuleAndReplacement(rule, replacement));
    }

    private static void singular(String rule, String replacement) {
        singulars.add(0, new RuleAndReplacement(rule, replacement));
    }

    private static void irregular(String singular, String plural) {
        plural(singular, plural);
        singular(plural, singular);
    }

    public static void uncountable(String... words) {
        uncountables.addAll(Arrays.asList(words));
    }
}

class RuleAndReplacement {
    private String rule;
    private String replacement;

    public RuleAndReplacement(String rule, String replacement) {
        this.rule = rule;
        this.replacement = replacement;
    }

    public String getReplacement() {
        return replacement;
    }

    public void setReplacement(String replacement) {
        this.replacement = replacement;
    }

    public String getRule() {
        return rule;
    }

    public void setRule(String rule) {
        this.rule = rule;
    }
}
