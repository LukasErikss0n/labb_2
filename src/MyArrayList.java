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
    //retunerar storleken på arrayn
    public int size() {
        return size;
    }

    @Override
    //returnerar false om arrayn inte är tom
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    // Tömmer listan trots att datan inte tas bort, genom att sätta size till noll kommer den att överskridas
    public void clear() {
        size = 0;
    }

    // -- 2 --

    //Utökar arrayns kapacitet för instansen
    public void ensureCapacity(int minCapacity) {
        if(minCapacity < dataArray.length){
            return;
        }
        E[] replaceArray = (E[]) new Object[minCapacity]; //temporär arrays skapas med en längre längd
        for(int i = 0; i < size; i++) {
            replaceArray[i] = dataArray[i];
        }
        dataArray = replaceArray;
    }

    //Tar bort outnyttjat kapacitet i arrayn
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

    //Lägger till ett element i arrayn på en specifik plats
    @Override
    public void add(int index, E element) throws IndexOutOfBoundsException {
        if(index < 0 || index > size){
            throw new IndexOutOfBoundsException("index out of bounce");
        }else if(size == dataArray.length){ //Ser till att elementet som ska läggs till får plats
            ensureCapacity(size +1);
        }
        E shift = dataArray[index]; //Tillfälligt sparar elementet vars index är samma som det nya elementet
        dataArray[index] = element; //Sparar nya elementet på platsen som de ska lägas till

        //Shiftar alla element (efter det nya) till höger
        for(int i = index; i < size; i++){
            E arraySplit = dataArray[i];
            dataArray[i+1] = shift;
            shift = arraySplit;
        }
        size++;
    }

    //Lägger till ett element sist i arrayn
    @Override
    public boolean add(E e) {
        if(size == dataArray.length){     //Om arrayn är full kallas ensureCapacity som utvidgar arrayn
            ensureCapacity(size+1);
        }
        dataArray[size] = e;
        size++;
        return true;
    }


    // -- 4 --
    @Override
    //Hämtar elementet i arrayn på indexen som efterfrågas
    public E get(int index) throws IndexOutOfBoundsException {
        if(index < 0 || index > size){
            throw new IndexOutOfBoundsException("index out of bounce");
        }
        return dataArray[index];
    }

    @Override
    //Ersätter elementet på specificerade platsen(index) i arrayn
    public E set(int index, E element) throws IndexOutOfBoundsException {
        if(index < 0 || index > size){
            throw new IndexOutOfBoundsException("index out of bounce");
        }
        E previous = get(index);
        dataArray[index] = element;
        return previous;
    }

    // -- 5 --
    //Tar bort ett element utifrån angivet index
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

    //Tar bort en range av element mellan två angivna index
    protected void removeRange(int fromIndex, int toIndex) throws IndexOutOfBoundsException {
        if(fromIndex < 0 || fromIndex >= size || toIndex > size || toIndex < fromIndex) {
            throw new IndexOutOfBoundsException("index out of bounce");
        }
        for(int i = fromIndex; i < toIndex; i++) { //Tar bort alla element från och med "fromIndex" till men inte "toIndex"
            remove(i);
            size--;
        }
    }

    // -- 6 --

    @Override
    //Tittar om ett efterfrågat element finns i listan, finns de flera tar det första elementet och returnar dess index
    //Returns -1 om det inte finns
    public int indexOf(Object o) {
        for(int i = 0; i < size; i++) {
            if(o == get(i)) {
                return i;
            }
        }
        return -1;
    }

    @Override
    //Tar bort första elementet av det angivna alementet
    public boolean remove(Object o) {
        int removeIndex = indexOf(o);
        if(removeIndex == -1){
            return false;
        }
        remove(removeIndex);
        return true;
    }

    @Override
    //tittar om elementet finns, anväder indexOf för att se om de finns, returnerar sedan en boolean
    public boolean contains(Object o) {
        if(indexOf(o) != -1) {
            return true;
        }
        return false;
    }

    // -- 7 --

    @Override
    //Skapar en "Shallow" kopia av array instansen. Den skapar inga nya element men den har pekare på elementet
    public Object clone() {
        MyArrayList<E> clone = new MyArrayList<E>(size);
        clone.dataArray = dataArray.clone();
        return clone;
    }

    @Override
    //Skapar en "Deep" kopia där alla element i arrayn kopieras över på en ny array
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
