package com.comms;

/**
 * Contains two generics values.
 * @param <T1> First value.
 * @param <T2> Second value.
 */
public class Tuple<T1, T2>
{
    private T1 value1;

    private  T2 value2;

    /**
     * Constructor.
     * @param value1 Fist value.
     * @param value2 Second Value.
     */
    public Tuple(T1 value1, T2 value2)
    {
        this.value1 = value1;
        this.value2 = value2;
    }

    /**
     * Get the first value
     * @return First value
     */
    public T1 getValue1()
    {
        return value1;
    }

    /**
     * Get the second value.
     * @return Second value.
     */
    public T2 getValue2()
    {
        return value2;
    }
}
