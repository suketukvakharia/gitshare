package tree;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

import org.junit.Test;

public class BuildPackages {
    
    @Test
    public void testCycle() {
        List<String> arg = Arrays.asList("a -> b","b -> c", "d -> e", "e -> f", "f -> a", "b -> d", "g -> z");
        
        List<String> buildOrder = this.buildPackages(arg);
        
        System.out.println(buildOrder);
    }
    
    @Test
    public void testSuccess() {
        List<String> arg = Arrays.asList("a -> b","b -> c", "d -> e", "e -> f", "f -> a", "g -> z");
        
        List<String> buildOrder = this.buildPackages(arg);
        
        System.out.println(buildOrder);
    }
    
    public List<String> buildPackages(List<String> packageDependencyList) {
        
        List<String> buildPackages = new LinkedList<>();
        
        Map<String, BuildPackage> packagesMap = new HashMap<>();
        Set<BuildPackage> rootPackage = new HashSet<>();
        Set<BuildPackage> consumerPackage = new HashSet<>();
        
        for(String dependency: packageDependencyList) {
            
            String[] split = dependency.split(" ");
            if(split.length < 3) {
                System.out.println("Bad entry:" + dependency);
                break;
            }
            
            String consumerStr = split[0], producerStr = split[2];
            
            BuildPackage consumer = getPackage(packagesMap, consumerStr),
                    producer = getPackage(packagesMap, producerStr);
            
            
            // cycle detection!!!! if producer already directly or indirectly depends on the consumer then there is a cycle.
            if(producer.dependsOn(consumer)) {
                System.out.println("Cycle detected");
                return Collections.emptyList();
            }
            
            consumer.dependencies.add(producer);
            producer.consumers.add(consumer);
            
            consumerPackage.add(consumer);
            rootPackage.remove(consumer);
            if(!consumerPackage.contains(producer)) {
                rootPackage.add(producer);
            }
        }
        
        // build the order of the packages to build through.
        
        Queue<BuildPackage> buildQueue = new LinkedList<>(rootPackage);
        
        
        while(!buildQueue.isEmpty()) {
            
            BuildPackage toBuild = buildQueue.remove();
            
            buildPackages.add(toBuild.name);
            
            // break the depedency tree and see if its consumers have no dependencies now.
            // consumer list is not cleaned up on purpose.
            for(BuildPackage consumer: toBuild.consumers) {
                consumer.dependencies.remove(toBuild);
                
                if(consumer.dependencies.isEmpty()) {
                    buildQueue.add(consumer);
                }
            }
        }
        
        return buildPackages;
    }
    
    private BuildPackage getPackage(Map<String, BuildPackage> packagesMap,
            String packageName) {
        if(packagesMap.containsKey(packageName)) {
            return packagesMap.get(packageName);
        }
        BuildPackage buildPackage = new BuildPackage(packageName);
        packagesMap.put(packageName, buildPackage);
        return buildPackage;
    }

    private class BuildPackage {
        public String name;
        public Set<BuildPackage> dependencies;
        
        public Set<BuildPackage> consumers; 
        
        public BuildPackage(String name) {
            this.name = name;
            this.dependencies = new HashSet<>();
            this.consumers = new HashSet<>();
        }
        public boolean dependsOn(BuildPackage consumer) {
            
            if(this.dependencies.contains(consumer)) {
                return true;
            }
            
            for(BuildPackage dependency: this.dependencies) {
                if(dependency.dependsOn(consumer)) {
                    return true;
                }
            }
            
            return false;
        }

        
        @Override
        public boolean equals(Object o) {
            if(!(o instanceof BuildPackage)) {
                return false;
            }
            return this.name.equals(((BuildPackage)o).name);
        }
    }

}
