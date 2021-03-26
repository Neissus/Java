package com.company;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

/**
 * This class stores the basic state necessary for the A* algorithm to compute a
 * path across a map.  This state includes a collection of "open waypoints" and
 * another collection of "closed waypoints."  In addition, this class provides
 * the basic operations that the A* pathfinding algorithm needs to perform its
 * processing.
 **/


public class AStarState
{
    /**This is a reference to the map that the A* algorithm is navigating.**/
    private final Map2D map;

    //Инициализируем хэшкарту открытых вершин и их местоположений
    private final HashMap<Location, Waypoint> openWaypoints = new HashMap<>();

    //Инициализируем хэшкарту открытых вершин и их местоположений
    private final HashMap<Location, Waypoint> closeWaypoints = new HashMap<> ();

    /**
     * Initialize a new state object for the A* pathfinding algorithm to use.
     **/
    public AStarState(Map2D map)
    {
        if (map == null)
            throw new NullPointerException("map cannot be null");
        this.map = map;
    }

    /** Returns the map that the A* pathfinder is navigating. **/
    public Map2D getMap()
    {
        return map;
    }

    /**
     Эта функция должна проверить все вершины в наборе открытых вершин,
     и после этого она должна вернуть ссылку на вершину с наименьшей общей стоимостью.
     Если в "открытом" наборе нет вершин, функция возвращает NULL.
     **/
    public Waypoint getMinOpenWaypoint()
    {
        // если в "открытом" наборе нет вершин, функция возвращает NULL
        if (numOpenWaypoints() == 0)
            return null;
        //инициализируем переменную для записи в нее списка всех ключей открытых вершин
        Set<Location> openWaypointKeys = openWaypoints.keySet();
        //инициализируем итератор для того, чтобы пройти по всем ключам
        Iterator<Location> i = openWaypointKeys.iterator();
        //инициализируем переменную для записи лучшей по цене вершины
        Waypoint best = null;
        //инициализируем переменную для записи лучшей цены
        float bestCost = Float.MAX_VALUE;

        //проверяем все открытые вершины
        while (i.hasNext())//возвращает true, если есть еще значения в списке
        {
            //запоминаем текущую позицию
            Location location = i.next();
            //запоминаем текущую вершину
            Waypoint waypoint = openWaypoints.get(location);
            //запоминаем стоимость текущей вершины
            float waypointTotalCost = waypoint.getTotalCost();

            //если стоимость текущей вершины меньше, то запоминаем текущую вершину,
            //как наиболее подходящую, и меняем лучшую стоимость
            if (waypointTotalCost < bestCost)
            {
                best = openWaypoints.get(location);
                bestCost = waypointTotalCost;
            }
        }
        //возвращаем вершину с лучшей (наименьшей) стоимостью
        return best;
    }

    /**
     * Если в наборе «открытых вершин» в настоящее время нет вершины
     * для данного местоположения, то метод добавляет новую вершину.
     *
     * Если в наборе «открытых вершин» уже есть вершина для этой локации,
     * метод добавляет новую вершину только в том случае,
     * если стоимость пути до новой вершины меньше стоимости пути до текущей.
     * Другими словами, если путь через новую вершину короче,
     * чем путь через текущую вершину, замените текущую вершину на новую
     **/
    public boolean addOpenWaypoint(Waypoint newWP)
    {
        //находим локацию новой вершины
        Location location = newWP.getLocation();

        //проверяем, есть ли в наборе открытых вершин вершина с такой локацией
        if (openWaypoints.containsKey(location))
        {
            //если вершина с такой локацией уже есть в наборе открытых вершин
            //стоимость пути должна быть меньше, чтобы мы записали ее снова
            Waypoint currentWaypoint = openWaypoints.get(location);
            if (newWP.getPreviousCost() < currentWaypoint.getPreviousCost())
            {
                //если стоимость пути до текущей точки меньше чем до предыдущей,
                //перезаписываем ее в набор открытых вершин и возвращаем true
                openWaypoints.put(location, newWP);
                return true;
            }
            //если стоимость пути до текущей точки неменьше чем до предыдущей, возвращаем true
            return false;
        }
        //если в наборе открытых вершин не было вершины с этой локацией,
        //записываем ее в набор и возвращаем true
        openWaypoints.put(location, newWP);
        return true;
    }


    /** Этот метод возвращает количество точек в наборе открытых вершин. **/
    public int numOpenWaypoints()
    {
        return openWaypoints.size();
    }

    /**
     * Этод метод перемещает вершину из набора открытых вершин
     * в набор закрытых вершин
    **/
    public void closeWaypoint(Location loc)
    {
        //записываем значение вершины удаляемой из набора открытых вершин
        Waypoint waypoint = openWaypoints.remove(loc);
        //добавляем в набор закрытых вершин, данную вершину и ее местоположение
        closeWaypoints.put(loc, waypoint);
    }

    /**
     * Этод метод возвращает значение true, если указанное местоположение
     * встречается в наборе закрытых вершин, и false в противном случае.
     **/
    public boolean isLocationClosed(Location loc)
    {
        return closeWaypoints.containsKey(loc);
    }
}