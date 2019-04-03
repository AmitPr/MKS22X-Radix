import java.util.Arrays;
import java.util.Iterator;

public class Radix {
    public static final int TRIALS = 5;
    public static void main(String[] args) {
        System.out.println("Size\t\tMax Value\tquick/builtin ratio ");
        int[] MAX_LIST = { 10, 500, 1000000000};
        for (int MAX : MAX_LIST) {
            for (int size = 31250; size < 2000001; size *= 2) {
                long qtime = 0;
                long btime = 0;
                // average of 5 sorts.
                for (int trial = 0; trial <= TRIALS; trial++) {
                    int[] data1 = new int[size];
                    int[] data2 = new int[size];
                    for (int i = 0; i < data1.length; i++) {
                        data1[i] = (int) (Math.random() * MAX);
                        data2[i] = data1[i];
                    }
                    long t1, t2;
                    t1 = System.currentTimeMillis();
                    Radix.radixsort(data2);
                    t2 = System.currentTimeMillis();
                    qtime += t2 - t1;
                    t1 = System.currentTimeMillis();
                    Arrays.sort(data1);
                    t2 = System.currentTimeMillis();
                    btime += t2 - t1;
                    if (!Arrays.equals(data1, data2)) {
                        System.out.println("FAIL TO SORT!");
                        System.exit(0);
                    }
                }
                System.out.println(size + "\t\t" + MAX + "\t" + 1.0 * qtime / btime);
            }
            System.out.println();
        }

    }

    public static void radixsort10(int[] data) {
        @SuppressWarnings("unchecked")
        MyLinkedList<Integer>[] buckets = (MyLinkedList<Integer>[]) new MyLinkedList[10];
        for (int i = 0; i < buckets.length; i++) {
            buckets[i] = new MyLinkedList<Integer>();
        }
        int largest = Integer.MIN_VALUE;
        int smallest = Integer.MAX_VALUE;
        int curDigit = 1;
        for (int i = 0; i < data.length; i++) {
            if (data[i] > largest)
                largest = data[i];
            if (data[i] < smallest)
                smallest = data[i];
            if (data[i] < 0)
                buckets[digitAt(data[i], curDigit)].addFirst(data[i]);
            else
                buckets[digitAt(data[i], curDigit)].addLast(data[i]);
        }
        if (Math.abs(smallest) > largest)
            largest = Math.abs(smallest);
        int maxDigit = (int) Math.ceil(Math.log10(largest));
        MyLinkedList<Integer> curWork = new MyLinkedList<Integer>();
        merge(curWork, buckets);
        curDigit++;
        while (curDigit <= maxDigit) {
            Iterator<Integer> it = curWork.iterator();
            while (it.hasNext()) {
                int x = it.next();
                if (x < 0)
                    buckets[digitAt(x, curDigit)].addFirst(x);
                else
                    buckets[digitAt(x, curDigit)].addLast(x);
            }
            curWork.clear();
            merge(curWork, buckets);
            curDigit++;
        }
        for (int i = 0; i < data.length; i++) {
            data[i] = curWork.removeFirst();
        }
    }

    public static void radixsort(int[] data) {
        @SuppressWarnings("unchecked")
        MyLinkedList<Integer>[] buckets = (MyLinkedList<Integer>[]) new MyLinkedList[16];
        for (int i = 0; i < buckets.length; i++) {
            buckets[i] = new MyLinkedList<Integer>();
        }
        int largest = Integer.MIN_VALUE;
        int smallest = Integer.MAX_VALUE;
        int curDigit = 0;
        for (int i = 0; i < data.length; i++) {
            int digit = data[i]&0xf;
            if (data[i] > largest)
                largest = data[i];
            if (data[i] < smallest)
                smallest = data[i];
            if (data[i] < 0)
                buckets[digit].addFirst(data[i]);
            else
                buckets[digit].addLast(data[i]);
        }
        if (Math.abs(smallest) > largest)
            largest = Math.abs(smallest);
        MyLinkedList<Integer> curWork = new MyLinkedList<Integer>();
        merge(curWork, buckets);
        curDigit+=4;
        while ((largest = (largest >>4)) > 0) {
            Iterator<Integer> it = curWork.iterator();
            while (it.hasNext()) {
                int x = it.next();
                int digit = (x>>curDigit)&0xf;
                if (x < 0)
                    buckets[digit].addFirst(x);
                else
                    buckets[digit].addLast(x);
            }
            curWork.clear();
            merge(curWork, buckets);
            curDigit+=4;
        }
        for (int i = 0; i < data.length; i++) {
            data[i] = curWork.removeFirst();
        }
    }

    private static void merge(MyLinkedList<Integer> into, MyLinkedList<Integer>[] buckets) {
        into.extend(buckets[0]);
        for (int i = 1; i < buckets.length; i++) {
            Iterator<Integer> it = buckets[i].iterator();
            while (it.hasNext()) {
                int x = it.next();
                if (x < 0)
                    into.addFirst(x);
                else
                    into.addLast(x);
            }
            buckets[i].clear();
        }
    }

    private static int digitAt(int n, int digit) {
        int d = (n / ((int) Math.pow(10, digit - 1))) % 10;
        return Math.abs(d);
    }
}