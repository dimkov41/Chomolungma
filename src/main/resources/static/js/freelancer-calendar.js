var Booking_Dates = [];

// HTML element to insert calendars into
var Element = document.getElementById('Booking_Calendars_Container');

/**
 * Options for Booking_Calendars
 * Object for build booking calendars
 */
var Options = {

    // set month names
    names_months: [
        'January', 'February', 'March', 'April',
        'May', 'June', 'July', 'August',
        'September', 'October', 'November', 'December'
    ],
    // set day names
    names_days: ['M', 'T', 'W', 'T', 'F', 'S', 'S'],
    // false or array of numbers: ex. [0,5] -> only first 6 months
    months_range: false, // [0,5] || false
    // year to process: Year in string 'yyyy' OR false for current year
    year: '2024', // 'yyyy' || false
    // objects array of booked dates
    // booked_dates: Booking_Dates,
    booked_dates: [{
    		date_start: '2024-03-01',
    		date_end: '2024-03-01',
    	},
    	{
    		date_start: '2024-03-04',
    		date_end: '2024-03-08',
    	},
    	{
    		date_start: '2024-04-01',
    		date_end: '2024-04-04',
    	},
    	{
    		date_start: '2024-04-06',
    		date_end: '2024-04-09',
    	},
    	{
    		date_start: '2024-04-09',
    		date_end: '2024-04-14',
    	},
    	{
    		date_start: '2024-05-01',
    		date_end: '2024-05-08',
    	}
    ]
};
/**
 * end Options for Booking_Calendars
 */

// Run the function !
Booking_calendars(Element, Options);