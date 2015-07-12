package com.archbrey.letters;

public class SortApps {

    public void exchange_sort(AppItem[] appItem){
        int i, j;
        AppItem temp;

        for ( i = 0;  i < appItem.length - 1;  i++ )
        {
            for ( j = i + 1;  j < appItem.length;  j++ )
            {
                if ( appItem [ i ].label.compareToIgnoreCase( appItem [ j ].label ) > 0 )
                {                                             // ascending sort
                    temp = appItem [ i ];
                    appItem [ i ] = appItem [ j ];    // swapping
                    appItem [ j ] = temp;

                } //if ( appItem [ i ].label.compareToIgnoreCase( appItem [ j ].label ) > 0 )
            } //for ( j = i + 1;  j < appItem.length;  j++ )
        } //for ( i = 0;  i < appItem.length - 1;  i++ )
    } //public void exchange_sort(AppItem[] appItem)

    public void exchange_sort(AppItem[] appItem, int length){
        int i, j;
        AppItem temp;

        for ( i = 0;  i < length - 1;  i++ )
        {
            for ( j = i + 1;  j < length;  j++ )
            {
                if ( appItem [ i ].label.compareToIgnoreCase( appItem [ j ].label ) > 0 )
                {                                             // ascending sort
                    temp = appItem [ i ];
                    appItem [ i ] = appItem [ j ];    // swapping
                    appItem [ j ] = temp;

                } //if ( appItem [ i ].label.compareToIgnoreCase( appItem [ j ].label ) > 0 )
            } //for ( j = i + 1;  j < length;  j++ )
        } //for ( i = 0;  i < length - 1;  i++ )
    } //public void exchange_sort(AppItem[] appItem)

}
