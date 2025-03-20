class MergeSortParallel implements Runnable {
    private Task[] tasks;
    private int left, right;

    public MergeSortParallel(Task[] tasks, int left, int right) {
        this.tasks = tasks;
        this.left = left;
        this.right = right;
    }

    @Override
    public void run() {
        if (left < right) {
            int mid = (left + right) / 2;

            Thread leftSorter = new Thread(new MergeSortParallel(tasks, left, mid));
            Thread rightSorter = new Thread(new MergeSortParallel(tasks, mid + 1, right));

            leftSorter.start();
            rightSorter.start();

            try {
                leftSorter.join();
                rightSorter.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            merge(tasks, left, mid, right);
        }
    }

    private void merge(Task[] tasks, int left, int mid, int right) {
        int leftSize = mid - left + 1;
        int rightSize = right - mid;

        Task[] leftHalf = new Task[leftSize];
        Task[] rightHalf = new Task[rightSize];

        for (int i = 0; i < leftSize; i++) {
            leftHalf[i] = tasks[left + i];
        }
        for (int j = 0; j < rightSize; j++) {
            rightHalf[j] = tasks[mid + 1 + j];
        }

        int i = 0, j = 0, k = left;
        while (i < leftSize && j < rightSize) {
            if (leftHalf[i].getPriority() <= rightHalf[j].getPriority()) {
                tasks[k] = leftHalf[i];
                i++;
            } else {
                tasks[k] = rightHalf[j];
                j++;
            }
            k++;
        }

        while (i < leftSize) {
            tasks[k] = leftHalf[i];
            i++;
            k++;
        }
        while (j < rightSize) {
            tasks[k] = rightHalf[j];
            j++;
            k++;
        }
    }
}
