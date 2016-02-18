import java.io.*;
import java.util.Arrays;

public class vdalavai_Float {

    private static final String[] all_structures = new String[] {"bst", "rbtree", "skiplist", "splaytree"};

    public static void main(String[] args) throws IOException {
        String file_name = "", data_structure = "";
        boolean print_mode = false, height = false;

        for (int i = 0; i < args.length; i++) {
            if (args[i].equals("-f"))
                file_name = args[i+1];
            else if (args[i].equals("-p"))
                print_mode = true;
            else if (args[i].equals("-h"))
                height = true;
            else if (args[i].equals("-d"))
                data_structure = args[i+1];
        }

        if (!Arrays.asList(all_structures).contains(data_structure)) {
            System.out.println("Data structure unknown");
            return;
        }

        File f = new File(file_name);
        if (!f.isFile()) {
            System.out.println("File does not exist : " + file_name);
            return;
        }

        if (data_structure.equals("bst")) {
            BST<Float> tree = new BST<Float>(); // Change this
            long totalTime = 0;

            BufferedReader br = new BufferedReader(new FileReader(new File(file_name)));
            String line;

            while ((line = br.readLine()) != null) {

                if (line.equals(""))
                    continue;

                Integer mode = Integer.valueOf(line.split("\t", 2)[0]);
                Float value =Float.parseFloat(line.split("\t",2)[1].trim());  // Change this

                long startTime = System.nanoTime();
                if (mode == 1) {
                    if (!tree.search(value)) {
                        tree.insert(value);
                    }
                } else if (mode == 0) {
                    tree.delete(value);
                } else {
                    System.out.println("Unknown parameter at position 1 in the input file");
                    return;
                }
                long endTime = System.nanoTime();
                totalTime += (endTime - startTime);
                if (height && !print_mode) {
                    tree.printHeight();
                }
            }
            if (print_mode && !height) {
                tree.print();
            }
            if (!print_mode && !height) {
                System.out.printf("Total time: %d milliseconds\n",totalTime/1000000);
                System.out.printf("Data structure contains %d elements\n", tree.getCount());
            }
            if (print_mode && height) {
                tree.printHeight();
                tree.print();
            }
            br.close();
        }

        if (data_structure.equals("rbtree")) {
            RBTREE<Float> tree = new RBTREE<Float>();  // Change this
            long totalTime = 0;

            BufferedReader br = new BufferedReader(new FileReader(new File(file_name)));
            String line;
            while ((line = br.readLine()) != null) {
                if (line.equals(""))
                    continue;
                Integer mode = Integer.valueOf(line.split("\t")[0]);
                Float value = Float.parseFloat(line.split("\t")[1].trim());  // Change this
                long startTime = System.nanoTime();
                if (mode == 1) {
                    if (!tree.search(value)) {
                        tree.insert(value);
                    }
                } else if (mode == 0) {
                	//System.out.println(line+"--------"+value);
                	tree.delete(value);
                } else {
                    System.out.println("Unknown parameter at position 1 in the input file");
                    return;
                }
                long endTime = System.nanoTime();
                totalTime += (endTime - startTime);
                if (height && !print_mode) {
                    tree.printHeight();
                }
            }
            if (print_mode && !height) {
                tree.print();
            }
            if (!print_mode && !height) {
                System.out.printf("Total time: %d milliseconds\n",totalTime/1000000);
                System.out.printf("Data structure contains %d elements\n", tree.getCount());
            }
            if (print_mode && height) {
                tree.printHeight();
                tree.print();
            }
            br.close();
        }

        if (data_structure.equals("skiplist")) {
            SkipList<Float> tree = new SkipList<Float>();  // Change this
            long totalTime = 0;

            BufferedReader br = new BufferedReader(new FileReader(new File(file_name)));
            String line;

            while ((line = br.readLine()) != null) {

                if (line.equals(""))
                    continue;

                Integer mode = Integer.valueOf(line.split("\t", 2)[0]);
                Float value = Float.parseFloat(line.split("\t",2)[1].trim());  // Change this

                long startTime = System.nanoTime();
                if (mode == 1) {
                    if (!tree.search(value)) {
                        tree.insert(value);
                    }
                } else if (mode == 0) {
                    tree.delete(value);
                } else {
                    System.out.println("Unknown parameter at position 1 in the input file");
                    return;
                }
                long endTime = System.nanoTime();
                totalTime += (endTime - startTime);
                if (height) {
                    System.out.println("-h is not a valid parameter for Skip list");
                    return;
                }
            }
            
            if (print_mode) {
                tree.print();
            }
            if (!print_mode) {
                System.out.printf("Total time: %d milliseconds\n", totalTime / 1000000);
                System.out.printf("Data structure contains %d elements\n", tree.getCount());
            }
            br.close();
        }

        if (data_structure.equals("splaytree")) {
            SplayTree<Float> tree = new SplayTree<Float>();  // Change this
            long totalTime = 0;

            BufferedReader br = new BufferedReader(new FileReader(new File(file_name)));
            String line;

            while ((line = br.readLine()) != null) {

                if (line.equals(""))
                    continue;

                Integer mode = Integer.valueOf(line.split("\t", 2)[0]);
                Float value = Float.parseFloat(line.split("\t",2)[1].trim());  // Change this

                long startTime = System.nanoTime();
                if (mode == 1) {
                    if (!tree.search(value)) {
                        tree.insert(value);
                    }
                } else if (mode == 0) {
                    tree.delete(value);
                } else {
                    System.out.println("Unknown parameter at position 1 in the input file");
                    return;
                }
                long endTime = System.nanoTime();
                totalTime += (endTime - startTime);
                if (height && !print_mode) {
                    tree.printHeight();
                }
            }
            if (print_mode && !height) {
                tree.print();
            }
            if (!print_mode && !height) {
                System.out.printf("Total time: %d milliseconds\n",totalTime/1000000);
                System.out.printf("Data structure contains %d elements\n", tree.getCount());
            }
            if (print_mode && height) {
                tree.printHeight();
                tree.print();
            }
            br.close();
        }
    }
}

