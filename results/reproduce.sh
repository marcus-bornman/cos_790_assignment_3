echo "Building the project..."
mvn clean compile assembly:single

echo "testing against src/resources/problems/tsp/ch150.tsp..."
java -jar target/cos_790_assignment_3-1.0-SNAPSHOT-jar-with-dependencies.jar -p src/resources/problems/tsp/ch150.tsp -g src/resources/genetic.parameters -o results/tsp/ch150.results -n 10
echo "testing against src/resources/problems/tsp/eil51.tsp..."
java -jar target/cos_790_assignment_3-1.0-SNAPSHOT-jar-with-dependencies.jar -p src/resources/problems/tsp/eil51.tsp -g src/resources/genetic.parameters -o results/tsp/eil51.results -n 10
echo "testing against src/resources/problems/tsp/eil101.tsp..."
java -jar target/cos_790_assignment_3-1.0-SNAPSHOT-jar-with-dependencies.jar -p src/resources/problems/tsp/eil101.tsp -g src/resources/genetic.parameters -o results/tsp/eil101.results -n 10
echo "testing against src/resources/problems/tsp/rat195.tsp..."
java -jar target/cos_790_assignment_3-1.0-SNAPSHOT-jar-with-dependencies.jar -p src/resources/problems/tsp/rat195.tsp -g src/resources/genetic.parameters -o results/tsp/rat195.results -n 10
echo "testing against src/resources/problems/tsp/rd400.tsp..."
java -jar target/cos_790_assignment_3-1.0-SNAPSHOT-jar-with-dependencies.jar -p src/resources/problems/tsp/rd400.tsp -g src/resources/genetic.parameters -o results/tsp/rd400.results -n 10

echo "testing against src/resources/problems/atsp/ftv170.atsp..."
java -jar target/cos_790_assignment_3-1.0-SNAPSHOT-jar-with-dependencies.jar -p src/resources/problems/atsp/ftv170.atsp -g src/resources/genetic.parameters -o results/atsp/ftv170.results -n 10
echo "testing against src/resources/problems/atsp/rbg323.atsp..."
java -jar target/cos_790_assignment_3-1.0-SNAPSHOT-jar-with-dependencies.jar -p src/resources/problems/atsp/rbg323.atsp -g src/resources/genetic.parameters -o results/atsp/rbg323.results -n 10
echo "testing against src/resources/problems/atsp/rbg358.atsp..."
java -jar target/cos_790_assignment_3-1.0-SNAPSHOT-jar-with-dependencies.jar -p src/resources/problems/atsp/rbg358.atsp -g src/resources/genetic.parameters -o results/atsp/rbg358.results -n 10
echo "testing against src/resources/problems/atsp/rbg403.atsp..."
java -jar target/cos_790_assignment_3-1.0-SNAPSHOT-jar-with-dependencies.jar -p src/resources/problems/atsp/rbg403.atsp -g src/resources/genetic.parameters -o results/atsp/rbg403.results -n 10
echo "testing against src/resources/problems/atsp/rbg443.atsp..."
java -jar target/cos_790_assignment_3-1.0-SNAPSHOT-jar-with-dependencies.jar -p src/resources/problems/atsp/rbg443.atsp -g src/resources/genetic.parameters -o results/atsp/rbg443.results -n 10

echo "Success! All tests have been completed successfully. See the 'results' folder for all output files."
