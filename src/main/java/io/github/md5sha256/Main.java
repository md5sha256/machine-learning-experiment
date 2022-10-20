package io.github.md5sha256;

import weka.classifiers.Classifier;
import weka.classifiers.Evaluation;
import weka.classifiers.functions.LinearRegression;
import weka.core.Attribute;
import weka.core.DenseInstance;
import weka.core.Instance;
import weka.core.Instances;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;

public class Main {
    public static void main(String[] args) {
        Logger.getAnonymousLogger().info("Starting runner");
        final Runner runner = new Runner(10000);
        runner.run();
    }

    private static class Runner {

        private static final Logger LOGGER = Logger.getLogger("Runner");

        private final int dataCount;

        public Runner(int dataCount) {
            this.dataCount = dataCount;
        }

        public void run() {
            var x = new Attribute("x");
            var y = new Attribute("y");
            var attributes = new ArrayList<>(Arrays.asList(x, y));
            var trainingData = new Instances("trainingData", attributes, 2000);
            trainingData.setClassIndex(trainingData.numAttributes() - 1);
            populateData(trainingData, x, y, 0, 20000);
            Classifier targetFunction = new LinearRegression();
            try {
                targetFunction.buildClassifier(trainingData);
            } catch (Exception ex) {
                ex.printStackTrace();
                LOGGER.warning("Failed to build classifier");
                return;
            }
            Instances validationData = new Instances("predictionData", attributes, this.dataCount);
            validationData.setClassIndex(validationData.numAttributes() - 1);
            populateData(validationData, x, y, 100000, this.dataCount);
            try {
                Evaluation evaluation = new Evaluation(trainingData);
                evaluation.evaluateModel(targetFunction, validationData);
                LOGGER.info(evaluation::toSummaryString);
            } catch (Exception ex) {
                ex.printStackTrace();
                LOGGER.warning("Failed to evaluate dataset");
            }
        }

        private static void populateData(Instances instances, Attribute attribute1, Attribute attribute2, int start, int count) {
            final List<Instance> tempInstances = new ArrayList<>(count);
            for (int i = start; i < count + start; i++) {
                Instance instance = new DenseInstance(2);
                instance.setValue(attribute1, i);
                instance.setValue(attribute2, i * 5D);
                tempInstances.add(instance);
            }
            instances.addAll(tempInstances);
        }

    }

}