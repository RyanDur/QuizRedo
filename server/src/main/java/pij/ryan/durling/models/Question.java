package pij.ryan.durling.models;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Set;

public interface Question extends Remote {

    /**
     * get the question
     *
     * @return a string
     * @throws RemoteException if problem with server
     */
    String getQuestion() throws RemoteException;

    /**
     * the score for the question
     *
     * @return an int
     * @throws RemoteException if problem with server
     */
    int getValue() throws RemoteException;

    /**
     * add an answer for the question
     *
     * @param answer takes an answer
     * @throws RemoteException if problem with server
     */
    void add(Answer answer) throws RemoteException;

    /**
     * check if the question is valid. i.e. if it has one or more answers
     *
     * @return true or false
     * @throws RemoteException if problem with server
     */
    boolean valid() throws RemoteException;

    /**
     * get a set of answers
     *
     * @return a set of answers
     * @throws RemoteException if problem with server
     */
    Set<Answer> getAnswers() throws RemoteException;
}
