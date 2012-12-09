/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package library.gui.tools;

import com.rits.cloning.Cloner;
import java.util.ArrayList;
import java.util.List;

/**
 * Memento class. For more information checkout memento software design pattern
 * @version 1.2
 * @author Dominik Szalai emptulik at gmail.com
 */
public class Memento<T>
{
    private List<T> states = new ArrayList<>();
    private Cloner cloner = new Cloner();
    private int currentState = -1;

    /**
     * Method used for adding additional state inside state holder. After object is inserted we set
     * pointer pointing to current state for latest inserted state
     * @param t 
     */
    public void setState(T t)
    {
        T temp = cloner.deepClone(t);
        states.add(temp);
        currentState = states.size();
    }
    
    
    /**
     * Method used for returning number of states, that is instance of memento holding.
     * @return number of states
     */
    public int getNumberStates()
    {
        return states.size();
    }
    
    /**
     * Method used for obtaining latest added state
     * @return latest state
     */
    public T getLastState()
    {
        if(states.isEmpty())
        {
            return null;
        }
        else
        {
            return states.get(states.size()-1);
        }        
    }
    
    /**
     * Method used for changing pointer for current state
     * @param stateIndex 
     */
    public void changeState(int stateIndex)
    {
        if(stateIndex < 0 || stateIndex>states.size())
        {
            throw new IllegalArgumentException("ERROR: passed stateindex is out of valid range");
        }
        currentState = stateIndex;
    }
    
    /**
     * Returns first state.
     * @return 
     */
    public T getFirstState()
    {
        if(states.isEmpty())
        {
            return null;
        }
        return states.get(0);
    }
    
    /**
     * Returns current state
     * @return 
     */
    public T getCurrentState()
    {
        if(states.isEmpty())
        {
            return null;
        }
        return states.get(currentState);
    }
    
    /**
     * Returns all states
     * @return 
     */
    public List<T> getAllStates()
    {
        return states;
    }
    
    /**
     * Switches to previous state
     * @return
     * @throws IndexOutOfBoundsException if we want to return state previous to first one. 
     */
    public T getPreviousState() throws IndexOutOfBoundsException
    {
        if(currentState == 0)
        {
            throw new IndexOutOfBoundsException("You are already at first state");
        }
        int t = currentState;
        currentState--;
        return states.get(t);
    }
    
    /**
     * Switches to next state
     * @return
     * @throws IndexOutOfBoundsException if we want to return state that is after last one. 
     */
    public T getNextState() throws IndexOutOfBoundsException
    {
        if(currentState == states.size())
        {
            throw new IndexOutOfBoundsException("You are already at last state");            
        }
        int t = currentState;
        currentState++;
        return states.get(t);
    }
}
