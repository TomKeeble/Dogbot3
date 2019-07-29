package com.tomkeeble.dogbot3.modules;

import java.util.*;

public class Chain {
    int state_size;
    Map<List<String>,Map<String,Integer>> model;
    List<Integer> begin_cumdist;
    List<String> begin_choices;
    public Chain(List<List<String>> corpus,int state_size){
        this(corpus,state_size,null);
    }
    public Chain(List<List<String>> corpus,int state_size,Map<List<String>,Map<String,Integer>> model){
        this.state_size = state_size;
        this.model = !(model==null) ? model : this.build(corpus, this.state_size);
        this.precompute_begin_state();
    }

    private void precompute_begin_state() {
        List<String>  begin_state = Collections.nCopies( state_size,"___BEGIN__");
        List<String> choices=new ArrayList<>(this.model.get(begin_state).keySet());
        List<Integer> weights=new ArrayList<>(this.model.get(begin_state).values());
        this.begin_cumdist = accumulate(weights);
        this.begin_choices = choices;
    }

    private List<Integer> accumulate(List<Integer> weights){
        List<Integer> totals=new ArrayList<Integer>();
        int runTot=0;
        for (int element:weights) {
            runTot+=element;
            totals.add(runTot);
        }
        return totals;
    }

    private Map<List<String>,Map<String,Integer>> build(List<List<String>> corpus,int state_size){
        model = new HashMap<List<String>,Map<String,Integer>>();

        for(List<String> run: corpus){
            List<String> items = new ArrayList<String>(Collections.nCopies( state_size,"___BEGIN__"));
            items.addAll(run);
            items.add("___END__");
            for (int i=0;i<=run.size();i++){
                List<String> state = items.subList(i,i+state_size);
                String follow = items.get(i+state_size);
                if (!model.containsKey(state)) {
                    model.put(state,new HashMap<String, Integer>());
                }
                if(!model.get(state).containsKey(follow)){
                    model.get(state).put(follow,0);
                }
                model.get(state).put(follow,model.get(state).get(follow)+1);
            }
        }

        return model;
    }

    public List<String> walk(List<String> init_state) {
        List<String> output=new ArrayList<String>();
        List<String> state = init_state!=null ? init_state : new ArrayList<>(Collections.nCopies( this.state_size,"___BEGIN__"));
        while (true) {
            String next_word = this.move(state);
            if (next_word.equals("___END__")){
                break;
            }
            output.add( next_word);
            state.remove(0);
            state.add(next_word);
        }
        return output;
    }

    private String move(List<String> state) {
        List<String> choices;
        List<Integer> cumdist;
        if(state == Collections.nCopies( this.state_size,"___BEGIN__")){
            choices = this.begin_choices;
            cumdist = this.begin_cumdist;
        }
        else{
            choices = new ArrayList<>(this.model.get(state).keySet());
            cumdist = accumulate(new ArrayList<>(this.model.get(state).values()));
        }
        double r =  Math.random() * cumdist.get(cumdist.size()-1);
        return choices.get(bisect_right(cumdist,r));
    }
    public static int bisect_right(List<Integer> A, double x) {
        return bisect_right(A, x, 0, A.size());
    }

    private static int bisect_right(List<Integer> A, double x, int lo, int hi) {
        while (lo < hi) {
            int mid = (lo+hi)/2;
            if (x < A.get(mid)) hi = mid;
            else lo = mid+1;
        }
        return lo;
    }
}
