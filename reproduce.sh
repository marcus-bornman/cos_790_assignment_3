echo "Building the project..."
mvn clean compile assembly:single

for ((num = 1; num <= 10; num++)); do
  echo "Test run $num executing on problems/exam_comp_set1.exam using Single-Point Search..."
  java -jar target/cos_790_assignment_2-1.0-SNAPSHOT-jar-with-dependencies.jar -s singlepoint -p problems/exam_comp_set1.exam -r $num -o results/exam_comp_set1/run_$num.singlepoint
  echo "Test run $num executing on problems/exam_comp_set2.exam using Single-Point Search..."
  java -jar target/cos_790_assignment_2-1.0-SNAPSHOT-jar-with-dependencies.jar -s singlepoint -p problems/exam_comp_set2.exam -r $num -o results/exam_comp_set2/run_$num.singlepoint
  echo "Test run $num executing on problems/exam_comp_set5.exam using Single-Point Search..."
  java -jar target/cos_790_assignment_2-1.0-SNAPSHOT-jar-with-dependencies.jar -s singlepoint -p problems/exam_comp_set5.exam -r $num -o results/exam_comp_set5/run_$num.singlepoint
  echo "Test run $num executing on problems/exam_comp_set6.exam using Single-Point Search..."
  java -jar target/cos_790_assignment_2-1.0-SNAPSHOT-jar-with-dependencies.jar -s singlepoint -p problems/exam_comp_set6.exam -r $num -o results/exam_comp_set6/run_$num.singlepoint
  echo "Test run $num executing on problems/exam_comp_set9.exam using Single-Point Search..."
  java -jar target/cos_790_assignment_2-1.0-SNAPSHOT-jar-with-dependencies.jar -s singlepoint -p problems/exam_comp_set9.exam -r $num -o results/exam_comp_set9/run_$num.singlepoint
  echo "Test run $num executing on problems/exam_comp_set10.exam using Single-Point Search..."
  java -jar target/cos_790_assignment_2-1.0-SNAPSHOT-jar-with-dependencies.jar -s singlepoint -p problems/exam_comp_set10.exam -r $num -o results/exam_comp_set10/run_$num.singlepoint

  echo "Test run $num executing on problems/exam_comp_set1.exam using Multi-Point Search..."
  java -jar target/cos_790_assignment_2-1.0-SNAPSHOT-jar-with-dependencies.jar -s multipoint -p problems/exam_comp_set1.exam -r $num -g genetic.parameters -o results/exam_comp_set1/run_$num.multipoint
  echo "Test run $num executing on problems/exam_comp_set2.exam using Multi-Point Search..."
  java -jar target/cos_790_assignment_2-1.0-SNAPSHOT-jar-with-dependencies.jar -s multipoint -p problems/exam_comp_set2.exam -r $num -g genetic.parameters -o results/exam_comp_set2/run_$num.multipoint
  echo "Test run $num executing on problems/exam_comp_set5.exam using Multi-Point Search..."
  java -jar target/cos_790_assignment_2-1.0-SNAPSHOT-jar-with-dependencies.jar -s multipoint -p problems/exam_comp_set5.exam -r $num -g genetic.parameters -o results/exam_comp_set5/run_$num.multipoint
  echo "Test run $num executing on problems/exam_comp_set6.exam using Multi-Point Search..."
  java -jar target/cos_790_assignment_2-1.0-SNAPSHOT-jar-with-dependencies.jar -s multipoint -p problems/exam_comp_set6.exam -r $num -g genetic.parameters -o results/exam_comp_set6/run_$num.multipoint
  echo "Test run $num executing on problems/exam_comp_set9.exam using Multi-Point Search..."
  java -jar target/cos_790_assignment_2-1.0-SNAPSHOT-jar-with-dependencies.jar -s multipoint -p problems/exam_comp_set9.exam -r $num -g genetic.parameters -o results/exam_comp_set9/run_$num.multipoint
  echo "Test run $num executing on problems/exam_comp_set10.exam using Multi-Point Search..."
  java -jar target/cos_790_assignment_2-1.0-SNAPSHOT-jar-with-dependencies.jar -s multipoint -p problems/exam_comp_set10.exam -r $num -g genetic.parameters -o results/exam_comp_set10/run_$num.multipoint
done

echo "Success! All tests have been completed successfully. See the 'results' folder for all output files."