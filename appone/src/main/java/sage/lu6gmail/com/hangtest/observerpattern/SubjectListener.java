package sage.lu6gmail.com.hangtest.observerpattern;

/**
 * 类名: SubjectListener
 * 此类用途: ---
 *
 * @Author: GuXiao
 * @Date: 2017-05-12 16:31
 * @Email: sage.lu6@gmail.com
 * @FileName: sage.lu6gmail.com.hangtest.observerpattern.SubjectListener.java
 */
public interface SubjectListener {
    void add(ObserverListener observerListener);

    void notifyObserver(String content);

    void remove(ObserverListener observerListener);
}
