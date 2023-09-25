package telran.employees;

import java.util.concurrent.locks.Lock;

public record Monitor(Lock read, Lock write) {

}
