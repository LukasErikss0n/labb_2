import java.io.Serializable;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.RandomAccess;
import java.util.Spliterator;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;

@SuppressWarnings("serial")
public class MyArrayList<E> implements Serializable, Cloneable, Iterable<E>,
        Collection<E>, List<E>, RandomAccess {

    // ---------------------------------------------------------------

    E[] dataArray;
    public static void main(String[] args) {
        MyArrayList<String> strlist = new MyArrayList<String>(17);

        // testa metoder härifrån

    }

    // ---------------------------------------------------------------

    private int size = 0;
    public MyArrayList(int initialCapacity) throws IllegalArgumentException {
            if( initialCapacity < 0) {
                throw new IllegalArgumentException("Initalcapacity need to be > 0");
            }
            dataArray = (E[]) new Object[initialCapacity]; // skapar en array med en lengd av en variabel
    }

    public MyArrayList() {
        this(10);
    }

    // -- 1 --

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() { //returnerar false om arrayn inte är tom
        return size == 0;
    }

    @Override
    public void clear() {
        /*for(int i = 0; i < size; i++ ){
            remove(i);
        }*/
        size = 0;
    }

    // -- 2 --

    public void ensureCapacity(int minCapacity) {
        if(minCapacity < dataArray.length){
            return;
        }
        E[] replaceArray = (E[]) new Object[minCapacity];
        for(int i = 0; i < size; i++){
            replaceArray[i] = dataArray[i];
        }
        dataArray = replaceArray;
    }

    public void trimToSize() {
        if(size == dataArray.length) {
            return;
        }
        E[] newList = (E[]) new Object();
        for(int i = 0; i<size; i++){
            newList[i] = dataArray[i];
        }


        dataArray = newList;
    }

    // -- 3 --

    @Override
    public void add(int index, E element) throws IndexOutOfBoundsException {
        if(index < 0 || index > size){
            throw new IndexOutOfBoundsException("index out of bounce");
        }else if(size == dataArray.length){
            ensureCapacity(size +1);
        }
        E shift = dataArray[index];
        dataArray[index] = element;

        for(int i = index; i < size; i++){
            E arraySplit = dataArray[i];
            dataArray[i+1] = shift;
            shift = arraySplit;
        }
        size++;
    }

    @Override
    public boolean add(E e) {
        if(size == dataArray.length){
            ensureCapacity(size+1);
        }
        dataArray[size] = e;
        size++;
        return true;
    }


    // -- 4 --

    @Override
    public E get(int index) throws IndexOutOfBoundsException {
        if(index < 0 || index > size){
            throw new IndexOutOfBoundsException("index out of bounce");
        }
        return dataArray[index];
    }

    @Override
    public E set(int index, E element) throws IndexOutOfBoundsException {
        if(index < 0 || index > size){
            throw new IndexOutOfBoundsException("index out of bounce");
        }
        E previous = get(index);
        dataArray[index] = element;
        return previous;
    }

    // -- 5 --

    @Override
    public E remove(int index) throws IndexOutOfBoundsException {
        if(index < 0 || index > size){
            throw new IndexOutOfBoundsException("index out of bounce");
        }
        E removed = dataArray[index];
        E[] shortend = (E[]) new Object[size-1];

        for(int i = 0; i < size - 1; i++){
            if(i == index){
                continue;
            }
            shortend[i] = dataArray[i];
        }
        dataArray = shortend;
        size--;
        return removed;
    }

    protected void removeRange(int fromIndex, int toIndex) {
        /* ska implementeras */
    }

    // -- 6 --

    @Override
    public int indexOf(Object o) {
        /* ska implementeras */
        return -1; /* bara med för att Eclipse inte ska klaga */
    }

    @Override
    public boolean remove(Object o) {
        /* ska implementeras */
        return false; /* bara med för att Eclipse inte ska klaga */
    }

    @Override
    public boolean contains(Object o) {
        /* ska implementeras */
        return false; /* bara med för att Eclipse inte ska klaga */
    }

    // -- 7 --

    @Override
    public Object clone() {
        /* ska implementeras */
        return null; /* bara med för att Eclipse inte ska klaga */
    }

    @Override
    public Object[] toArray() {
        E[] newArray = (E[]) new Object[size];
        for(int i = 0; i < size; i++) {
            newArray[i] = get(i);
        }
        return newArray;
    }

    // --- Rör ej nedanstående kod -----------------------------------

    public MyArrayList(Collection<? extends E> c) {
        throw new UnsupportedOperationException();
    }

    private class InternalIterator implements Iterator {
        int current = 0;

        @Override
        public boolean hasNext() {
            return current < size();
        }

        @Override
        public Object next() {
            return get(current++);

        }

    }

    @Override
    public Iterator<E> iterator() {
        return new InternalIterator();
    }

    @Override
    public boolean addAll(int index, Collection<? extends E> c) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        throw new UnsupportedOperationException();
    }

    @Override
    public int lastIndexOf(Object o) {
        throw new UnsupportedOperationException();
    }

    @Override
    public <T> T[] toArray(T[] a) {
        throw new UnsupportedOperationException();
    }

    @Override
    public ListIterator<E> listIterator(int index) {
        throw new UnsupportedOperationException();
    }

    @Override
    public ListIterator<E> listIterator() {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<E> subList(int fromIndex, int toIndex) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void forEach(Consumer<? super E> action) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Spliterator<E> spliterator() {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean removeIf(Predicate<? super E> filter) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void replaceAll(UnaryOperator<E> operator) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void sort(Comparator<? super E> c) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        throw new UnsupportedOperationException();
    }

}
