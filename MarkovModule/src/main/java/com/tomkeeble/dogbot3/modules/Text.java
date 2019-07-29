package com.tomkeeble.dogbot3.modules;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.sun.tools.corba.se.idl.toJavaPortable.Arguments.None;

public class Text {
    String rejoined_text;
    boolean well_formed;
    int state_size;
    Pattern reject_pat=Pattern.compile("(^')|('$)|\\s'|'\\s|[\\\"(\\(\\)\\[\\])]");
    boolean retain_original;
    List<List<String>> parsed_sentences;

    Chain chain;

    public Text(List<String> input_text){
        this(input_text,2,null,null,true,true,"");
    }
    public Text(List<String> input_sentences, int state_size, Chain chain, List<List<String>> parsed_sentences, Boolean retain_original, boolean well_formed, String reject_reg){
        this.well_formed = well_formed;
        if (well_formed && !reject_reg.equals("")){
            this.reject_pat = Pattern.compile(reject_reg);
        }
        boolean can_make_sentences = parsed_sentences!=null || input_sentences!=null;
        this.retain_original = retain_original && can_make_sentences;
        this.state_size = state_size;

        if (this.retain_original){
            this.parsed_sentences = parsed_sentences!=null ? parsed_sentences : this.generate_corpus(input_sentences);
            List<String> intermediate = new ArrayList<String>();
            for(List<String> sentence:this.parsed_sentences){
                intermediate.add(String.join(" ",sentence));
            }
            this.rejoined_text = String.join(" ",intermediate);
            this.chain = chain!=null ? chain : new Chain(this.parsed_sentences, state_size);
        }
        else{
            if (chain==null){
                List<List<String>> parsed = parsed_sentences!=null ? parsed_sentences : this.generate_corpus(input_sentences);
                this.chain =new Chain(parsed, state_size);
            }
            else{
                this.chain = chain;
            }
        }

    }
    public String make_sentence(){
        final int tries = 20;
        final double mor = 0.7d;
        final int mot = 15;

        for (int i=0;i<tries;i++) {
            List<String> words = this.chain.walk(null);
            if (rejoined_text!=null){
                if (testSentenceOutput(words, mor, mot)){
                    return String.join(" ",words);
                }
            }else{
                return String.join(" ",words);
            }
        }
        return "Not enough source data.";
    }

    private List<List<String>> generate_corpus(List<String> input_text) {
        List<String> sentences = new ArrayList<String>();
        for(String sentence:input_text){
            List<String> split_sentence=this.sentence_split(sentence);
            for(String split:split_sentence){
                sentences.add(split);
            }
        }
        List<String> passing = new ArrayList<String>();
        for(String sentence:sentences){
            if(this.test_sentence_input(sentence)){
                passing.add(sentence);
            }
        }
        List<List<String>> runs = new ArrayList<List<String>>();
        for(String sentence:passing){
            runs.add(this.word_split(sentence));
        }
        return runs;
    }

    private boolean test_sentence_input(String sentence) {
        if(sentence.trim().length() == 0) {
            return false;
        }
        if (this.well_formed && this.reject_pat.matcher(sentence).find()){
            return false;
        }
        return true;
    }

    private boolean testSentenceOutput(List<String> words,double mor, int mot){
        int overlapRatio = (int)(Math.round(mor * words.size()));
        int overlapMax = Math.min(mot, overlapRatio);
        int overlapOver = overlapMax + 1;
        int gram_count = Math.max((words.size() - overlapMax), 1);
        List<List<String>> grams = new ArrayList<List<String>>(){};
        for (int i=0;i<gram_count;i++){
            grams.add(words.subList(i,i+overlapOver));
        }
        for (List<String> g:grams){
            String gram_joined = String.join(" ",g);
            if (this.rejoined_text.contains(gram_joined)) {
                return false;
            }
        }
        return true;
    }
    private List<String> word_split(String sentence) {
        String[] words=Pattern.compile("\\s+").split(sentence);
        return Arrays.asList(words);
    }

    private List<String> sentence_split(String sentence) {
        Pattern potential_end_pat = Pattern.compile(String.join("", new String[]{"([\\w\\.'’&\\]\\)]+[\\.\\?!])","([‘’“”'\"\\)\\]]*)","(\\s+(?![a-z\\-–—]))"}),Pattern.UNICODE_CHARACTER_CLASS);
        Matcher dot_iter = potential_end_pat.matcher(sentence);
        List<Integer> end_indices = new ArrayList<Integer>();
        end_indices.add(0);
        while(dot_iter.find()){
            if (is_sentence_ender(dot_iter.group(1))) {
                end_indices.add(dot_iter.start() + dot_iter.group(1).length() + dot_iter.group(2).length());
            }
        };
        end_indices.add(sentence.length());
        List<String> sentences=new ArrayList<String>();
        for(int i=0;i<end_indices.size()-1;i++){
            sentences.add(sentence.substring(end_indices.get(i),end_indices.get(i+1)).trim());
        }
        return sentences;
    }

    private boolean is_sentence_ender(String group) {
        List<String> exceptions= Arrays.asList("U.S.,U.N.,E.U.,F.B.I.,C.I.A.".split(","));
        if (exceptions.contains(group)){
            return false;
        }
        if(group.endsWith("?") ||group.endsWith("!")){
            return true;
        }
        if (group.replaceAll("[^A-Z]", "").length()>1){
            return true;
        }
        if(group.endsWith("?")&& !is_abbreviation(group)){
            return true;
        }
        return false;
    }

    private boolean is_abbreviation(String group) {
        String ascii="abcdefghijklmnopqrstuvwxyz";
        List<String> abbr_capped = Arrays.asList(String.join(",", new String[]{
                "ala,ariz,ark,calif,colo,conn,del,fla,ga,ill,ind,kan,ky,la,md,mass,mich,minn,miss,mo,mont,neb,nev,okla,ore,pa,tenn,vt,va,wash,wis,wyo",
                "u.s",
                "mr,ms,mrs,msr,dr,gov,pres,sen,sens,rep,reps,prof,gen,messrs,col,sr,jf,sgt,mgr,fr,rev,jr,snr,atty,supt",
                "ave,blvd,st,rd,hwy",
                "jan,feb,mar,apr,jun,jul,aug,sep,sept,oct,nov,dec",
                String.join(",", ascii)
        }).split(","));

        String clipped = group.substring(0,group.length()-1);
        return ascii.toUpperCase().contains(clipped.charAt(0)+"") ? abbr_capped.contains(clipped.toLowerCase()):"etc|v|vs|viz|al|pct".contains(clipped);
    }

    ;
}
