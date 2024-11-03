/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Uranus;

/**
 *
 * @author takina
 */
public enum TControl {
        a('a', 1), 
        b('b', 2),
        c('c', 3),
        d('d', 4),
        e('e', 5),
        f('f', 6),
        g('g', 7),
        h('h', 8),
        i('i', 9),
        j('j', 10),
        k('k', 11),
        l('l', 12),
        m('m', 13);

    private char value;
    private int  pos;


    private TControl(char value, int pos) {
        this.value = value;
        this.pos   = pos;
    }

    public char getValue() {
        return this.value;
    }
    
    public int getPos() {
        return this.pos;
    }    
}
